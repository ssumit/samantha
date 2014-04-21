wit.ai based personal assistant.

Three things were attempted:

1) Alarm support:
2) Calling someone by name or number
3) location based reminder.

Usage examples:
Call Sam.
Call 134.
I want to call to Steve.
remind me to buy oranges at K-Mart.


Following are the issues faced some of which have not been solved due to lack of time:
1) It is difficult to capture time like 6:15 (quarter past six).
2) Number two ("2") becomes "to".
3) Before using API, sometimes we need to pre-process the data. When calling uer says call 81303 and we might have some
spaces in between along with point (2).
4) Locations might not be what user has in mind so again an intelligent filtering required.
5) Identifying nouns especially non-english

Other comments:
I was working on IntelliJ idea. I used the sdk though one can easily write a wrapper over http API provided.
1) forces me to use gson lib.. I actually had a wrapper over gson which now I cant use
2) forces me to use support package..
3) only when I executed the app using android sdk, I came to knew of its dependence on org.apache.commons.io
4) layout: wit_button.xml is expected
.

enhancement expected:
1) API should return the possible set of missing arguments.. e.g. the user says: " wake me up at 6", then missing args should be am/pm.
2) Requires internet for every call. (Can we come up with offline usage via periodically downloading some component?)
Not a big issue since we have internet connection for most of the time.