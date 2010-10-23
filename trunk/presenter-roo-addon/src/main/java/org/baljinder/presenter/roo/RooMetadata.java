package org.baljinder.presenter.roo;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.classpath.PhysicalTypeIdentifierNamingUtils;
import org.springframework.roo.classpath.PhysicalTypeMetadata;
import org.springframework.roo.classpath.details.DefaultFieldMetadata;
import org.springframework.roo.classpath.details.DefaultMethodMetadata;
import org.springframework.roo.classpath.details.FieldMetadata;
import org.springframework.roo.classpath.details.MethodMetadata;
import org.springframework.roo.classpath.details.annotations.AnnotatedJavaType;
import org.springframework.roo.classpath.details.annotations.AnnotationMetadata;
import org.springframework.roo.classpath.itd.AbstractItdTypeDetailsProvidingMetadataItem;
import org.springframework.roo.classpath.itd.InvocableMemberBodyBuilder;
import org.springframework.roo.metadata.MetadataIdentificationUtils;
import org.springframework.roo.model.JavaSymbolName;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.project.Path;
import org.springframework.roo.support.style.ToStringCreator;
import org.springframework.roo.support.util.Assert;

/**
 * Metadata to be triggered by annotation
 * 
 * @since 1.1.0.M2
 *
 */
public class RooMetadata extends AbstractItdTypeDetailsProvidingMetadataItem {

	private static final String PROVIDES_TYPE_STRING = RooMetadata.class.getName();
	private static final String PROVIDES_TYPE = MetadataIdentificationUtils.create(PROVIDES_TYPE_STRING);

	public RooMetadata(String identifier, JavaType aspectName, PhysicalTypeMetadata governorPhysicalTypeMetadata) {
		super(identifier, aspectName, governorPhysicalTypeMetadata);
		Assert.isTrue(isValid(identifier), "Metadata identification string '" + identifier + "' does not appear to be a valid");
		
		if (!isValid()) {
			return;
		}
		
		builder.addField(getSampleField());
			
		builder.addMethod(getSampleMethod());
		
		// Create a representation of the desired output ITD
		itdTypeDetails = builder.build();
	}
		
	private FieldMetadata getSampleField() {
		return new DefaultFieldMetadata(getId(), Modifier.PRIVATE, new JavaSymbolName("sampleField"), new JavaType(String.class.getName()), null, new ArrayList<AnnotationMetadata>());
	}
	
	private MethodMetadata getSampleMethod() {
		JavaSymbolName methodName = new JavaSymbolName("sampleMethod");
		MethodMetadata method = methodExists(methodName, new ArrayList<AnnotatedJavaType>());
		if (method != null) {
			return method;
		}
		
		List<AnnotatedJavaType> parameters = new ArrayList<AnnotatedJavaType>();
		
		List<JavaSymbolName> parameterNames = new ArrayList<JavaSymbolName>();
		
		List<AnnotationMetadata> annotations = new ArrayList<AnnotationMetadata>();
		
		List<JavaType> throwsTypes = new ArrayList<JavaType>();
		
		InvocableMemberBodyBuilder body = new InvocableMemberBodyBuilder();
		body.appendFormalLine("System.out.println(\"Hello World\");");
		
		return new DefaultMethodMetadata(getId(), Modifier.PUBLIC, methodName, JavaType.VOID_PRIMITIVE, parameters, parameterNames, annotations, throwsTypes, body.getOutput());
	}
		
	private MethodMetadata methodExists(JavaSymbolName methodName, List<AnnotatedJavaType> paramTypes) {
		// We have no access to method parameter information, so we scan by name alone and treat any match as authoritative
		// We do not scan the superclass, as the caller is expected to know we'll only scan the current class
		for (MethodMetadata method : governorTypeDetails.getDeclaredMethods()) {
			if (method.getMethodName().equals(methodName) && method.getParameterTypes().equals(paramTypes)) {
				// Found a method of the expected name; we won't check method parameters though
				return method;
			}
		}
		return null;
	}
	
	public String toString() {
		ToStringCreator tsc = new ToStringCreator(this);
		tsc.append("identifier", getId());
		tsc.append("valid", valid);
		tsc.append("aspectName", aspectName);
		tsc.append("destinationType", destination);
		tsc.append("governor", governorPhysicalTypeMetadata.getId());
		tsc.append("itdTypeDetails", itdTypeDetails);
		return tsc.toString();
	}

	public static final String getMetadataIdentiferType() {
		return PROVIDES_TYPE;
	}
	
	public static final String createIdentifier(JavaType javaType, Path path) {
		return PhysicalTypeIdentifierNamingUtils.createIdentifier(PROVIDES_TYPE_STRING, javaType, path);
	}

	public static final JavaType getJavaType(String metadataIdentificationString) {
		return PhysicalTypeIdentifierNamingUtils.getJavaType(PROVIDES_TYPE_STRING, metadataIdentificationString);
	}

	public static final Path getPath(String metadataIdentificationString) {
		return PhysicalTypeIdentifierNamingUtils.getPath(PROVIDES_TYPE_STRING, metadataIdentificationString);
	}

	public static boolean isValid(String metadataIdentificationString) {
		return PhysicalTypeIdentifierNamingUtils.isValid(PROVIDES_TYPE_STRING, metadataIdentificationString);
	}
}
