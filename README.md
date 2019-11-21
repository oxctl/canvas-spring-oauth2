Canvas Spring Security OAuth2
=============================

[![Build Status](https://travis-ci.com/oxctl/canvas-spring-oauth2.svg?branch=master)](https://travis-ci.com/oxctl/canvas-spring-oauth2)

This project contains a few supporting files for building Spring Security OAuth2 client that work well with the [Canvas](https://www.instructure.com/canvas/) LMS by [Instructure](https://www.instructure.com/). There is documentation of their [OAuth2](https://canvas.instructure.com/doc/api/file.oauth.html) setup which may be helpful in understanding this library.

Using
-----

This project is release to the central maven repository and can be added as a maven dependency to another project:

            <dependency>
                <groupId>uk.ac.ox.ctl</groupId>
                <artifactId>canvas-spring-oauth2</artifactId>
                <version>0.2</version>
            </dependency>

Building
--------

To build this library use [Apache Maven](https://maven.apache.org):

    mvn install

This will build a JAR that can be used as a dependency in another project.

Releasing
---------

This project is deployed to the central repository, once ready to release you can have the release plugin tag everything:

    mvn -Prelease release:clean release:prepare
    
then if that completes successfully a release bundle can be pushed to the staging area of the Sonatype OSS repository with:

    mvn -Prelease release:perform
    
We don't automatically close the staged artifacts so after checking that the files are ok you can login to the [repository](https://oss.sonatype.org/) and release it.

License
-------

This project is Apache 2 licensed.
