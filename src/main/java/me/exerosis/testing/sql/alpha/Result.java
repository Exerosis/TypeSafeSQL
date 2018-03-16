package me.exerosis.testing.sql.alpha;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import static java.nio.charset.Charset.defaultCharset;

public interface Result {
	//--Lang--
	byte asByte();
	
	short asShort();
	
	char asChar();
	
	int asInt();
	
	float asFloat();
	
	long asLong();
	
	double asDouble();
	
	String asString();
	
	
	//--SQL--
	Blob asBlob();
	
	Timestamp asTimestamp();
	
	Date asDate();
	
	Time asTime();
	
	URL asUrl();
	
	
	//--Binary--
	interface AsBinary {
		byte[] array(Charset charset);
		
		default byte[] array() {
			return array(defaultCharset());
		}
		
		default InputStream stream(Charset charset) {
			return new ByteArrayInputStream(array(charset));
		}
		
		default InputStream stream() {
			return stream(defaultCharset());
		}
	}
	
	default AsBinary asBinary() {
		return charset -> asString().getBytes(charset);
	}
}