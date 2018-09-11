# Notes développeur

## Repo Maven local

Pour ajouter un JAR/WAR SmartGuide dans le repo local au projet, utiliser les
commandes suivantes (à adapter) :

    mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=<fichier> -DgroupId=com.alphinat.smartguide -DartifactId=smartlets-api5 -Dversion=5.0.0 -Dpackaging=jar -DlocalRepositoryPath=mavenLocalRepo/
    mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=<fichier> -DgroupId=com.alphinat.smartguide -DartifactId=smartlets -Dversion=6.5.0 -Dpackaging=war -DlocalRepositoryPath=mavenLocalRepo/
    mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=<fichier> -DgroupId=com.alphinat.smartguide -DartifactId=sgd -Dversion=6.5.0 -Dpackaging=war -DlocalRepositoryPath=mavenLocalRepo/

## Exemple de configuration datasource Tomcat

    
        A ajouter dans server.xml:
        
      <Resource name="jdbc/sigif"
         global="jdbc/sigif"
         auth="Container"
         type="javax.sql.DataSource"
         driverClassName="com.mysql.jdbc.Driver"
         url="jdbc:mysql://localhost:3306/sigif"
         username="sigif"
         password="sigif"
         maxActive="100"
         maxIdle="20"
         minIdle="5"
         maxWait="10000"/>
         
         A ajouter dans context.xml:
         
         <ResourceLink name="jdbc/sigif"
                         global="jdbc/sigif"
                         auth="Container"
                         type="javax.sql.DataSource" />

# Notes de versions

## Version 1.0 (xx/xx/2017) `sigif-1.0`

Version initiale

### Notes de livraison

TODO