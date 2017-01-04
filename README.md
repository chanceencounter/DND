*Final semester project

#DND
###Major Function pieces of the project
1. UI  
  1. Player (Client)
    * Show them the important information about their character and events
  2. DM (Server)
    * Show important information about all characters at a glance, allow triggering actions easily
2. Backend (Business Logic)  
  1. Player (Client)
    * Probably want to have as _simple_ clients as much as possible
  2. DM (Server)
    * Should handle validating actions and applying effects
  3. Shared components
    * Decide on logging library (recommend SLF4J)
3. Infrastructure  
  * Basic pojo library (recommend Immutables.io)
4. Networking
  * Support handshake (connect and authenticate/register as player)
  * Support broadcast (ability to send same data to multiple clients)
  * Support direct message (ability to send data to specific client)
  * Define information payload types (start small, make it extensible)

###Tasks
1. ~~Set up git repo and push current code there~~
2. ~~Get basic pojo library (http://immutables.github.io/) set up and working~~
  * ~~Add maven artifact to deps~~
  * ~~Create simple example~~
  * ~~Verify that maven is including generated sources properly~~
3. ~~Get SLF4J maven artifact imported~~
4. ~~Create enum of data types and associated immutables pojo’s to transmit data between client and server.  ~~
5. ~~Get serialization and deserialization working for sending pojo’s over the wire.~~

###Other Tasks
After submitting the semester project clean up code with the following:

1. Sanitize inputs
2. Write unit tests, look into JUnit
  * Refactor code to make it more conducive to testing
