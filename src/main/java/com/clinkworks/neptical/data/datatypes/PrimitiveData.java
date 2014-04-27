package com.clinkworks.neptical.data.datatypes;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface PrimitiveData extends Data{
	
	public abstract boolean getAsBoolean();

	public abstract Number getAsNumber();

	public abstract String getAsString();

	public abstract double getAsDouble();

	public abstract float getAsFloat();

	public abstract long getAsLong();

	public abstract int getAsInt();

	public abstract byte getAsByte();

	public abstract char getAsCharacter();

	public abstract BigDecimal getAsBigDecimal();

	public abstract BigInteger getAsBigInteger();

	public abstract short getAsShort();
}
