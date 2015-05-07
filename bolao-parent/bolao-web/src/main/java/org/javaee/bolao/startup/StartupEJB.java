package org.javaee.bolao.startup;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.javaee.bolao.config.IResources;
import org.javaee.bolao.partida.PartidaTimer;

import com.googlecode.flyway.core.Flyway;

@Singleton
@Startup
public class StartupEJB {

	@Resource(lookup = IResources.DATASOURCE)
	private DataSource dataSource;

	@Resource(name = "migrationLocation")
	private String migrationLocation = "db.migration.mysql";
	
	@Inject
	private PartidaTimer partidaTimer;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@PostConstruct
	private void init() {
		initFlyWay();
		//initTimersPartida();
	}

	private void initTimersPartida() {
		partidaTimer.restaurarTimers();
	}

	private void initFlyWay() {
		Flyway flyway = new Flyway();
		flyway.setInitOnMigrate(true);
		
		flyway.setLocations(migrationLocation);
		
		flyway.setDataSource(dataSource);

		// for Hibernate Envers
		// flyway.setSchemas(IResources.SCHEMA, IResources.SCHEMA_AUD);

		logger.info("Flyway.migrate: " + flyway.migrate());

	}

}
