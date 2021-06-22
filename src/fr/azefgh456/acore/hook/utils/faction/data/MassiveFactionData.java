package fr.azefgh456.acore.hook.utils.faction.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.azefgh456.acore.hook.utils.faction.impl.MassiveFaction;
import fr.azefgh456.acore.hook.utils.faction.impl.faction.MFaction;
import fr.azefgh456.acore.hook.utils.faction.impl.faction.stats.MassiveFactionStats;
import fr.azefgh456.acore.storage.requette.sync.IContainRequette;
import fr.azefgh456.acore.storage.requette.sync.IInsertRequette;
import fr.azefgh456.acore.storage.requette.sync.ISelectRequette;
import fr.azefgh456.acore.storage.requette.sync.ITruncateRequette;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.DataBuilder;

public class MassiveFactionData extends Data<MFaction>{

	private MassiveFaction factionM;
	
	public MassiveFactionData(MassiveFaction factionInstance) {
		super("factionsStorage", "uuid VARCHAR(100) primary key, name VARCHAR(100), tuers INT(10), morts INT(10)");
		this.factionM = factionInstance;
	}

	@Override
	public void load() {
		Map<String, MassiveFactionStats> factionsStats = new HashMap<>();
		DataBuilder datas = factionM.executeCustomTypeRequette(new ISelectRequette(storageName, Arrays.asList("uuid", "tuers", "morts")));
		datas.getDatas().forEach(factionStats -> {
			factionsStats.put((String)factionStats.get("uuid"), new MassiveFactionStats((int)factionStats.get("tuers"), (int)factionStats.get("morts")));
		});
		factionM.setFactionsStats(factionsStats);
	}

	@Override
	public void save() {
		factionM.simpleRequette(new ITruncateRequette(storageName));
		
		for(Entry<String, MassiveFactionStats> factionStats : factionM.getFactionsStats().entrySet()) {
			factionM.simpleRequette(new IInsertRequette(storageName, Arrays.asList("uuid", "name", "tuers", "morts"), Arrays.asList(factionStats.getKey(), factionM.getFactionById(factionStats.getKey()).getFactionName(), factionStats.getValue().getTuers(), factionStats.getValue().getMorts())));
		}
	}

	@Override
	public boolean contain(MFaction val) {
		return factionM.containRequette(new IContainRequette(storageName, Arrays.asList("uuid"), Arrays.asList(val.getFactionId())));
	}

}
