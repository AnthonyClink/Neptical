package clinkworks.neptical.domain;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import javax.inject.Provider;

import clinkworks.neptical.Data;
import clinkworks.neptical.datatype.Cursor;
import clinkworks.neptical.datatype.DataDefinitionException;
import clinkworks.neptical.datatype.DataModule;
import clinkworks.neptical.datatype.Location;
import clinkworks.neptical.datatype.NepticalData;

public class NSpace implements DataModule, Location {

	private final Provider<Cursor> cursorProvider;
	private final String name;
	private final Map<String, DataModule> dataModules;
	
	private List<String> fragments;

	public NSpace(String name, String... dataModules){
		this(Data.getCursorContext(), name, dataModules);
	}
	
	public NSpace(Provider<Cursor> cursorProvider, String name, String... dataModules) {
		this(cursorProvider, name, Stream.of(dataModules).map((moduleName) -> new GenericDataModule(moduleName))
				.toArray(DataModule[]::new));
	}

	NSpace(Provider<Cursor> cursorProvider, String name, DataModule... dataModules) {
		this.dataModules = new ConcurrentHashMap<>(dataModules.length);

		// hold the search order as Nspaces has different semantics than Modules
		fragments = new CopyOnWriteArrayList<>();

		for (int i = 0; i < dataModules.length; i++) {
			DataModule dataModule = dataModules[i];
			String moduleName = dataModule.getName();
			this.dataModules.put(moduleName, dataModule);
			fragments.add(moduleName);
		}
		this.cursorProvider = cursorProvider;
		this.name = name;
	}

	public boolean containsModule(String module){
		return fragments.contains(module);
	}
	
	public void defineModules(String... moduleNames) {

		fragments.clear();

		for (String moduleName : moduleNames) {
			fragments.add(moduleName);
			if (getDataModule(moduleName) == null) {
				dataModules.put(moduleName, new GenericDataModule(moduleName));
			}
		}

	}
	
	public void addModule(String moduleName){
		int currentFragmentIndex = fragments.indexOf(moduleName);
		
		if(currentFragmentIndex < 0){
			fragments.add(moduleName);
			dataModules.put(moduleName, new GenericDataModule(moduleName));
			return;
		}
		
		fragments.add(fragments.remove(currentFragmentIndex));
		
	}

	public void addData(String segment, Object data) throws DataDefinitionException {
		getCurrentModule().addData(segment, new GenericImmutableData(data));
	}
	
	public void addData(String dataModule, String segment, Object data) throws DataDefinitionException {
		getDataModule(dataModule).addData(segment, new GenericImmutableData(data));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] segments() {
		return fragments.toArray(new String[fragments.size()]);
	}

	@Override
	public void addData(String segment, NepticalData data) throws DataDefinitionException {
		DataModule dataModule = getCurrentModule();
		if (dataModule == null) {
			throw new DataDefinitionException("NSpace " + getName()
					+ " does not have a registered data module, call addData(Module, Segment, Data) to register and add this data");
		}
		dataModule.addData(segment, data);
	}

	@Override
	public List<NepticalData> getData(String segment) {
		DataModule dataModule = getCurrentModule();

		if (dataModule == null) {
			return new ArrayList<>();
		}

		List<NepticalData> moduleData = dataModule.getData(segment);

		if (!moduleData.isEmpty()) {
			return moduleData;
		}
		
		for (int i = fragments.size() - 1; i > 0; i--) {
			moduleData = getDataModule(fragments.get(i - 1)).getData(segment);
			if (moduleData.isEmpty()) {
				continue;
			}
			return moduleData;
		}
		
		return new ArrayList<>();

	}

	@Override
	public NepticalData getData(String segment, int index) {
		DataModule dataModule = getDataModuleContaining(segment);
		if(dataModule == null){
			return NepticalData.NULL_DATA;
		}
		return dataModule.getData(segment, index);
	}

	@Override
	public List<NepticalData> getData() {

		if (fragments.isEmpty()) {
			return new ArrayList<>();
		}

		return getDataModule(fragments.get(fragments.size() - 1)).getData();

	}
	
	public DataModule getDataModuleContaining(String segment){
				
		for(int i = fragments.size(); i > 0; i--){
			DataModule dataModule = getDataModule(fragments.get(i - 1));
			
			if(dataModule.getData(segment).isEmpty()){
				continue;
			}
			
			return dataModule;
		}
		
		return null;
	}

	private DataModule getCurrentModule() {
		if (fragments.isEmpty()) {
			return null;
		}
		return getDataModule(fragments.get(fragments.size() - 1));
	}

	public DataModule getDataModule(String moduleName) {
		return dataModules.get(moduleName);
	}

	@Override
	public DataModule parentModule() {
		return this;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Cursor moveCursorHere() {
		return cursorProvider.get().moveToLocation(this);
	}


	@Override
	public NepticalData get() {
		return NepticalData.NULL_DATA;
	}

	@Override
	public URI getResourceIdentity() {
			try {
				return new URI("neptical://" + name());
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public int rowId() {
		return -1;
	}



}
