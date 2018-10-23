/*******************************************************************************
 * Copyright (c) 2013-2018 Contributors to the Eclipse Foundation
 *   
 *  See the NOTICE file distributed with this work for additional
 *  information regarding copyright ownership.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Apache License,
 *  Version 2.0 which accompanies this distribution and is available at
 *  http://www.apache.org/licenses/LICENSE-2.0.txt
 ******************************************************************************/
package org.locationtech.geowave.adapter.vector;

import org.locationtech.geowave.core.index.ByteArrayId;
import org.locationtech.geowave.core.store.adapter.NativeFieldHandler;
import org.opengis.feature.simple.SimpleFeature;

/**
 * This is used by the FeatureDataAdapter to handle GeoWave 'fields' using
 * SimpleFeature 'attributes.'
 * 
 */
public class AvroFeatureAttributeHandler implements
		NativeFieldHandler<SimpleFeature, Object>
{
	public static final ByteArrayId FIELD_ID = new ByteArrayId(
			"foo");

	public AvroFeatureAttributeHandler() {}

	@Override
	public ByteArrayId getFieldId() {
		return FIELD_ID;
	}

	@Override
	public Object getFieldValue(
			final SimpleFeature row ) {
		return row;
	}
}