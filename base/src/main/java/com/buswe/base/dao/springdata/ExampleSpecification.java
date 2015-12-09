package com.buswe.base.dao.springdata;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;


public class ExampleSpecification <T> implements Specification<T>{
	private static final Logger logger = LoggerFactory.getLogger(ExampleSpecification.class);
    protected final List<Predicate> expressions = Collections.synchronizedList(new ArrayList<Predicate>());
	EntityManager entityManager;
	T example;
	
	public ExampleSpecification(final EntityManager entityManager, final T example) {
		this.entityManager = entityManager;
		this.example = example;
	}
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
ManagedType<T> managedType = entityManager.getMetamodel().managedType((Class<T>) example.getClass());
ComparisonStyle style = new ComparisonStyle.Default();
CompareByExample(cb, managedType, root, example, style);
if (expressions.size() == 0) {
    logger.warn("query by example running with no criteria");
}

return cb.and(expressions.toArray(new Predicate[expressions.size()]));
	}

	  private <X> void CompareByExample(CriteriaBuilder builder, ManagedType<X> type,
              Path<X> from, X instance, ComparisonStyle style,
              Attribute<?, ?>... excludes) {
 List<Attribute<?, ?>> excludeAttr = excludes == null
         ? new ArrayList<Attribute<?,?>>() : Arrays.asList(excludes);
 Set<SingularAttribute<? super X, ?>> attrs = type.getSingularAttributes();
 for (SingularAttribute<? super X, ?> attr : attrs) {
     if (excludeAttr.contains(attr)
             || (style.excludeIdentity() && attr.isId())
             || (style.excludeVersion() && attr.isVersion())) {
         continue;
     }
     Object value = extractValue(instance, attr);
     if ((style.excludeNull() && value == null)
             || (style.excludeDefault() && isDefaultValue(attr.getJavaType(), value)))
         continue;

     Predicate p = null;
     if (value == null) {
         p = from.get(attr).isNull();
         expressions.add(p);
         continue;
     }
     if (attr.isAssociation()) {
         ManagedType<X> declaringType = (ManagedType<X>) attr.getDeclaringType();
         Path<X> path = (Path<X>)from.get(attr);
         X v = (X)value;
         CompareByExample(builder, declaringType, path, v, style, excludes);
     } else if (attr.getJavaType() == String.class) {
         Expression<String> s = from.get(attr).as(String.class);
         switch (style.getStringComparsionMode()) {
             case EXACT : p = builder.equal(s, value);
                 break;
             case CASE_INSENSITIVE : p = builder.equal(builder.upper(s), value.toString());
                 break;
             case LIKE: p = builder.like(s, value.toString());
                 break;
         }
     } else {
         p = builder.equal(from.get(attr), value);
     }
     expressions.add(p);
 }
}
 
      <X> Object extractValue(X instance, SingularAttribute<? super X, ?> attr) {
          Class<?> cls = instance.getClass();
          Field[] fields = getAllFields(cls);
          for (Field field : fields){
              if(!field.getName().equals(attr.getName())){
                  continue;
              }
              if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())){
                  continue;
              }
              field.setAccessible(true);
              Object value = null;
              try {
                  value = field.get(example);
                  return value;
              } catch (IllegalAccessException e){
                  Object[] args = {field.getName(), example.getClass(), e};
                  logger.debug("FAILED TO ACCESS FIELD [%s] ON CLASS [%s]. Cause: %s", args);
              }
          }
          return null;
      }
      
      private Field[] getAllFields(Class clazz){
          if(clazz == null){
              return null;
          }

          List<Field> list = new ArrayList<>();
          list.addAll(Arrays.asList(clazz.getDeclaredFields()));
          while (clazz != Object.class){
              clazz = clazz.getSuperclass();
              list.addAll(Arrays.asList(clazz.getDeclaredFields()));
          }

          return list.toArray(new Field[list.size()]);
      }
      
      boolean isDefaultValue(Class<?> cls, Object val) {
          if (val == null) {
              return true;
          }
          if (cls == Boolean.class || cls == boolean.class) {
              return Boolean.FALSE.equals(val);
          } else if (cls == Character.class || cls == char.class) {
              return ((Character) val).charValue() == 0;
          } else if (cls == Byte.class || cls == byte.class
                  || cls == Double.class || cls == double.class
                  || cls == Float.class || cls == float.class
                  || cls == Long.class || cls == long.class
                  || cls == Integer.class || cls == int.class
                  || cls == Short.class || cls == short.class) {
              return ((Number) val).intValue() == 0;
          } else if (cls == String.class) {
              return "".equals(val);
          } else {
              return false;
          }
      }
}
