Files in here become available to applications by being copied to webapps/Dynamide/WEB-INF/lib
via the .war file.
You can also make them available to javac via
    javac -extdirs ./dynamdide.lib
or something similar.  Or you can use Ant, which can be passed a lib directory for the javac task.

=========== Log ==============================================================

1/7/2002 4:03 AM Installed webmacro.jar, which is webmacro 1.0, in both dynamide/lib and jdk14b3/jre/lib/ext.

3/19/2002 11:03AM Added jws.jar to dynamide/lib, to make it find javax.servlet.Servlet
    otherwise when you comile against webmacro 1.0, this call causes a compilation error:
            m_webmacro = new org.webmacro.WM("com/dynamide/WebMacroDynamide.properties");
    whereas this doesn't
            m_webmacro = new org.webmacro.new WM();

3/26/2002 3:57PM jdom.jar is jdom beta 8 rc1

7/6/2002 12:27PM Removed jws.jar, and use the jsdk23.jar that comes with resin.

7/6/2002 12:28PM Renamed tcl.jar.hide to tcl.jar, because I think I was just hiding it to see if it worked.

7/10/2002 7:44PM Renamed tcl.jar to tcl.jar.hide, because it is the old one.  the new set is jacl.jar and tcljava.jar.

Copied C:\bin\dom4j\sandbox\dom4j\build\dom4j-full.jar
    This is from my dom4j sandbox, which later became build 1.3 on the public release.
    I contributed some code to dom4j to make it more XHTML-friendly.

9/3/2002 7:21PM  updated to webmacro.jar version 1.0.1

9/8/2002 9:51AM  added C:\bin\jakarta-tomcat-4.0.4\common\lib\naming-common.jar to get JNDI helper classes and implementation.

9/12/2002 9:11AM added castor jars from castor version 0.9.3.9 : castor-0.9.3.9-xml.jar and castor-0.9.3.9.jar
                 Note that these depend on the xerces stuff, notably org/apache/xml/serialize/XMLSerializer

5/19/2003 6:57AM Added skaringa-r1p7.jar to get com.skaringa.javaxml.* used by com.dynamide.util.SkaringaTools
                 which does Object <===> XML transformations.

5/30/2003 3:02PM Added mail.jar from http://java.sun.com/products/javamail/
                 I was using it out of
                    C:\bin\jdkee14\lib\j2ee.jar
                 but that required having that in the classpath.
                 installed activation.jar, which is required.

9/30/2003 10:26PM  Added pg73jdbc3.jar from http://jdbc.postgresql.org/download.html

10/20/2003       Added bitmechanic jdbcpool

10/20/2003       Added loadtester.jar, although this is 3rd party and is not licensed and must not be part of the
                    distribution

10/30/2003 10:07AM  Upgraded to beanshell 1.2b8, but not to 1.3.

4/11/2004 Upgraded to JDOM b10 RC1.  Seems to have solved some memory leaks.  Annoying API changes.



12/18/2007  I had to remove Saxon.jar
  ParserConfigurationException: AElfred parser is namespace-aware
  http://www.javacorporate.com/pipermail/opensource/2002-March/016766.html
  
12/19/2007
   I instead renamed saxon.jar to zsaxon.jar, so that it wouldn't conflict.  This is a platform-specific kludge, I think,
     when you rely on package name order for loading order.

=========== Locations, where you can get jar files ===========================

beanshell from
    Beanshell 1.2b3
    http://www.beanshell.org
    http://www.beanshell.org/download.html
    http://www.beanshell.org/bsh-1.2b3.jar
    http://www.beanshell.org/bsh-1.2b8.jar
       NOTE: 1.3 (and 2.x) has a difference in how the class manager/loader works, so it breaks dynamide


Servlet API from:
    C:\bin\resin-cmp-1.0.5\lib\jsdk23.jar

Dom4j-full.jar
    C:\bin\dom4j\sandbox\dom4j\build\dom4j-full.jar
    http://www.dom4j.org
    http://www.dom4j.org/download.html
    http://prdownloads.sourceforge.net/dom4j/dom4j-1.3.zip
    extract
        dom4j-full.jar

xercesImpl.jar
xmlParserAPIs.jar
    from xerces 2.0.2 release
    http://xml.apache.org/xerces2-j/index.html
    http://xml.apache.org/dist/xerces-j/
    Just get the latest production files.
    extract the files:
        xerces-2_0_2/xercesImpl.jar
        xerces-2_0_2/xmlParserAPIs.jar

webmacro.jar
    http://www.webmacro.org
    http://www.webmacro.org/DownloadWebMacro
    http://www.webmacro.org/webmacro/webmacro-1.0.1.zip
    extract
        webmacro.jar

naming-common.jar
    http://jakarta.apache.org/tomcat
    http://jakarta.apache.org/builds/jakarta-tomcat-4.0/release/v4.0.4/bin/jakarta-tomcat-4.0.4.zip
    extract this file:
        jakarta-tomcat-4.0.4/common/lib/naming-common.jar

Saxon
    http://users.iclway.co.uk/mhkay/saxon/

castor-xml.jar
castor.jar
    castor.org

mail.jar
    http://java.sun.com/products/javamail/
    C:\bin\javamail-1.3

activation.jar
    JavaBeans(TM) Activation Framework 1.0.2
    http://java.sun.com/products/javabeans/glasgow/jaf.html

datatable.jar
    From C:\bin\webmacro\webmacro\contrib\DataTable\jars\datatable.jar
      but I built it with some fixes.

pg73jdbc3.jar
    From: http://jdbc.postgresql.org/download.html
    and you can build from source (I haven't) in C:\install\postgresql\postgresql-7.3.4\src\interfaces\jdbc\

    jdbc connection pool
      jdbcpool-0.99.tar.gz
    from
      http://www.bitmechanic.com/projects/jdbcpool/#download

1/15/2005 1:17PM
    nekohtml from:
    http://www.apache.org/~andyc/neko/doc/html/

    Depended on Xalan

    httpunit from:
        http://httpunit.sourceforge.net/

    Built nekohtml.jar and put it into C:\install\httpunit\httpunit-1.6\lib

2010-03-31
    Added client jars for apache xmlrpc: 
        commons-logging-1.1.jar
        ws-commons-util-1.0.2.jar
        xmlrpc-client-3.1.3.jar
        xmlrpc-common-3.1.3.jar
