package com.buswe.base.utils;

public enum FiletypeEnum
{
  jpg("FFD8FF"),  png("89504E47"),  gif("47494638"),  tif("49492A00"),  bmp("424D"),  dwg("41433130"),  html("68746D6C3E"),  rtf("7B5C727466"),  xml("3C3F786D6C"),  zip("504B0304"),  rar("52617221"),  psd("38425053"),  eml("44656C69766572792D646174653A"),  dbx("CFAD12FEC5FD746F"),  pst("2142444E"),  xls("D0CF11E0"),  doc("D0CF11E0"),  mdb("5374616E64617264204A"),  wpd("FF575043"),  eps("252150532D41646F6265"),  ps("252150532D41646F6265"),  pdf("255044462D312E"),  qdf("AC9EBD8F"),  pwl("E3828596"),  wav("57415645"),  avi("41564920"),  ram("2E7261FD"),  rm("2E524D46"),  mpg("000001BA"),  mov("6D6F6F76"),  asf("3026B2758E66CF11"),  mid("4D546864");
  
  private String filetypeHex;
  
  private FiletypeEnum(String filetypeHex)
  {
    this.filetypeHex = filetypeHex;
  }
  
  public String getValue()
  {
    return this.filetypeHex;
  }
}
