package com.Richardson.wff.helpers;

public class NoArgConstructorUndefinedException extends RuntimeException
{
  Class cls;

  public NoArgConstructorUndefinedException(Class paramClass)
  {
    this.cls = paramClass;
  }

  public String getClassName()
  {
    return this.cls.getName();
  }
}