<h1>PhoneBook</h1>
A simple phone book written with Spring MVC and AngularJS. <br/>
Basicaly PhoneBook is a simple porject, an implementation of a basic, multi-user phonebook. 
<h2>The main technology that was used:</h2>
<ol>
    <li>Apache Maven</li>
    <li>Spring 4</li>
    <li>Spring IO Platform</li>
    <li>Spring Security</li>
    <li>Spring MVC</li>    
    <li>Spring Data JPA</li>  
    <li>JMS</li>
    <li>Spring Batch</li>
    <li>Spring Integration</li>
    <li>Tyhmeleaf</li>
    <li>AngularJS</li>
    <li>Bootstrap</li>
    <li>WebJars</li>
    <li>Apache POI</li>
    <li>iText</li>
</ol>
<p>
In this repository I have implemented two version. In either version the business logic are the same but 
the configuartion was in the two most famous way to configure Spring: xml and javaConfig <br/>

In the app an user can either log-in, if already he have an account, or create an new account. 
This security and profiling layer was implemented in <a href="http://projects.spring.io/spring-security/" target="_blank">Spring Security.</a> 
Exist two profile: USER and ADMIN. The USER can managed the his phone book adding new contact and managed his user profile. An Admin 
on the other hand can see all the registered user and forcing the password reset. All the page was implemented with Thymeleaf, as CSS I had used Titter Bootstrap 
and for REST service consuming I had used AngularJS, the dependency of the static assets of third part I had used the webjars porject that through a lot of maven dependency managed for you 
the static assets of third part. This project wasn't a web app single page but in many ponit I had used ws rest through angular and a very interesting the modern way that used @RestController and the new 
approch of Spring 4 with ResponseEntity builder approch for the ws response of the controller. The service of creation of the phoonebook in a printable way, the sing-up and the password reset service
was implemented through a spring integration pipeline. With those services I can show how implement the same things either in xml and javaConfig with the new way, Spring Integration JavaDSL using java8 lambda expression.
For the prepopolation of db, embedded mail server and system/mail utilities I had implemented a Batch, implemented in Spring Batch, that starting from a csv retrive all the information about a user and register the user in mail server and the phonebook system.
</p>

<h1>Running instructions</h1>
<p>
 In this show-case I used the Spring load-waving time together with Spring configured abstraction.
 With this two component through @Configurable, I can inject a Spring bean in a non Spring bean. 
 The PhoneBookUserBuilder is a stateful object and for this reason I have create a new builder for any new user. 
 The technique resolve a quite hard problem. Since that the builder is a statefull object we can't think it as a singleton and even the prototype scope don't help.
 Actually we needed of an istruments that perform a dependency injection of the password encoder for all new created builder. The techniwue used resolve it problem and
 I can inject the itself spring been that I configured for spring security. for this reason we have add the -javaagent:path of a copy of the jar\spring-instrument.jar 
 in the start up command of tomcat or any other web/application server in which we deploy the phonebook application
</p>
