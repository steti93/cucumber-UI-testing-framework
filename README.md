To initiate test , please create a runner and specify following property for them:


-Dbrowser=firefox -Dspring.profiles.active=demo


To work on Internet Explorer is requared to do some security changes on IE browser, follow the instructions:

1.Open IE
2.Go to Tools -> Internet Options -> Security
3.Set all zones (Internet, Local intranet, Trusted sites, Restricted sites) to the same protected mode, enabled or disabled should not matter.
4.Finally, set Zoom level to 100% by right clicking on the gear located at the top right corner and enabling the status-bar.Default zoom level is now displayed at the lower right.


For jenkins configuration :

To run UI tests:

mvn clean install -Pui-tests -Dbrowser=firefox -Dspring.profiles.active=demo
