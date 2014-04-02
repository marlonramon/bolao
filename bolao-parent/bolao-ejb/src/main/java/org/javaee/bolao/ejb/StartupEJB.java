package org.javaee.bolao.ejb;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

import com.googlecode.flyway.core.Flyway;

@Singleton
@Startup
public class StartupEJB {

	@Resource(lookup = IResources.DATASOURCE)
	private DataSource dataSource;

	@Resource(name = "migrationLocation")
	private String migrationLocation = "db.migration.mysql";
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@PostConstruct
	private void init() {
		initFlyWay();
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
