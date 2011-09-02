1. Installation der Datenbank
Als Datenbank wird Apache Cassandra in der Version 0.74 verwendet: http://cassandra.apache.org

1.2 Datenbank herunterladen und in ein beliebiges Verzeichnis entpacken. 
	Die beiden Dateien access.properties und password.properties müssen in das conf/ Verzeichnis der Datenbank kopiert werden.
	Danach müssen in der Datei cassandra.bat die JAVA_OPTS ergänzt werden. Folgende Zeilen ans Ende anfügen:
	 -Dpasswd.properties=%CASSANDRA_HOME%\conf/passwd.properties^
     -Daccess.properties=%CASSANDRA_HOME%\conf/access.properties

1.3 Die Datei conf/cassandra.yaml
	authenticator: org.apache.cassandra.auth.SimpleAuthenticator
	authority: org.apache.cassandra.auth.SimpleAuthority

1.4 Datenbank starten
	Aufruf cassandra.bat

1.5 Datenbank anlegen
	Aufruf cassandra-cli.bat
	
	Befehle ausführen:
	connect localhost/9160 admin 'ad5532wfe';
	create keyspace AdServer;
	
	connect localhost/9160 adserver 'ads938234c1';
	use AdServer;
	create column family TrackEvent with column_type=Super and comparator=TimeUUIDType and subcomparator=UTF8Type;
	create column family Click with comparator=TimeUUIDType;
	create column family Impression with comparator=TimeUUIDType;
	
	
2. MongoDB
	AdServer verwendet MongoDB 1.8.0
	
2.1. Installation
		Einfach entpacken
		MongoDb erzeugt die Verzeichnis nicht beim starten, daher müssen sie angelegt werden
	
2.2. Konfiguration in bin kopieren

2.3. Starten
		mongod -f mongo.conf