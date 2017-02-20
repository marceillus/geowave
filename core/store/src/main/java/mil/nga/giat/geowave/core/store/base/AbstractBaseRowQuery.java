package mil.nga.giat.geowave.core.store.base;

import org.apache.log4j.Logger;

import mil.nga.giat.geowave.core.store.CloseableIterator;
import mil.nga.giat.geowave.core.store.CloseableIteratorWrapper;
import mil.nga.giat.geowave.core.store.DataStoreOptions;
import mil.nga.giat.geowave.core.store.adapter.AdapterStore;
import mil.nga.giat.geowave.core.store.callback.ScanCallback;
import mil.nga.giat.geowave.core.store.data.visibility.DifferingFieldVisibilityEntryCount;
import mil.nga.giat.geowave.core.store.index.PrimaryIndex;
import mil.nga.giat.geowave.core.store.operations.DataStoreOperations;
import mil.nga.giat.geowave.core.store.operations.Reader;
import mil.nga.giat.geowave.core.store.operations.ReaderClosableWrapper;
import mil.nga.giat.geowave.core.store.operations.ReaderParams;
import mil.nga.giat.geowave.core.store.util.NativeEntryIteratorWrapper;

/**
 * Represents a query operation by an Accumulo row. This abstraction is
 * re-usable for both exact row ID queries and row prefix queries.
 *
 */
abstract class AbstractBaseRowQuery<T> extends
		BaseQuery
{
	private static final Logger LOGGER = Logger.getLogger(AbstractBaseRowQuery.class);
	protected final ScanCallback<T, ?> scanCallback;

	public AbstractBaseRowQuery(
			final BaseDataStore dataStore,
			final PrimaryIndex index,
			final String[] authorizations,
			final ScanCallback<T, ?> scanCallback,
			final DifferingFieldVisibilityEntryCount visibilityCounts ) {
		super(
				dataStore,
				index,
				visibilityCounts,
				authorizations);
		this.scanCallback = scanCallback;
	}

	public CloseableIterator<T> query(
			final DataStoreOperations operations,
			final DataStoreOptions options,
			final double[] maxResolutionSubsamplingPerDimension,
			final AdapterStore adapterStore ) {
		Reader reader = operations.createReader(new ReaderParams(
				index,
				adapterIds,
				maxResolutionSubsamplingPerDimension,
				getAggregation(),
				getFieldSubsets(),
				isMixedVisibilityRows(),
				isServerSideAggregation(options),
				getRanges(),
				getServerFilter(options),
				getScannerLimit(),
				getCoordinateRanges(),
				getConstraints(),
				getAdditionalAuthorizations()));
		return new CloseableIteratorWrapper(
				new ReaderClosableWrapper(
						reader),
				new NativeEntryIteratorWrapper(
						dataStore,
						adapterStore,
						index,
						reader,
						getClientFilter(options),
						scanCallback,
						!isCommonIndexAggregation()));
	}

	abstract protected Integer getScannerLimit();
}
