# How to get token for VK API
* Register in *vk.com*
* Log in
* Go to *vk.com/apps*
* Add app
* Go to *app settings*
* Save *app ID*
* Go to *http://oauth.vk.com/authorize?client_id=ENTER_APP_ID_HERE&scope=photos,docs,wall&response_type=token*
* Get *access_token* value from URL
# How to start
Use *src/test/resources/suite.xml* to start all cases.  
Also, it's possible to start each test separately.
# TestCase001
* **Step 1** [UI] Go to *VK* website
* **Step 2** [UI] Log In
* **Step 3** [UI] Go to *My Profile* page
* **Step 4** [API] Create a wall post using API with random text and get *Post ID* from API response
* **Step 5** [UI] Check the wall post is displayed
* **Step 6** [API] Edit the wall post using API - edit post text and add image file
* **Step 7** [UI] Check the wall post is edited
* **Step 8** [API] Add a comment to the wall post using API
* **Step 9** [UI] Check the comment is added
* **Step 10** [UI] Like the wall post
* **Step 11** [API] Check the wall post is liked using API
* **Step 12** [API] Delete the wall post using API
* **Step 13** [UI] Check the wall post is deleted
* **Post Condition** Delete uploaded photo
***
# TestCase002
* **Step 1** [UI] Go to *VK* website
* **Step 2** [UI] Log In
* **Step 3** [UI] Go to *My Profile* page
* **Step 4** [UI] Get user URL
* **Step 5** [UI] Get user ID
* **Step 6** [API] Create a wall post using API with random text and get *Post ID* from API response
* **Step 7** [UI] Check the wall post is displayed
* **Step 8** [UI] Like the wall post
* **Step 9** [API] Check the wall post is liked using API
* **Step 10** [UI] Log out
* **Step 11** [UI] Open *VK* website
* **Step 12** [UI] Log In
* **Step 13** [UI] Go to user page via URL (*Step 4*)
* **Step 14** [UI] Check the wall post is displayed
* **Step 15** [UI] Unlike the wall post.
* **Step 16** [API] Check the wall post isn't liked using API
* **Post Condition** Delete uploaded photo
***
# TestCase003
* **Step 1** [UI] Go to *VK* website
* **Step 2** [UI] Log In
* **Step 3** [UI] Go to *My Profile* page
* **Step 4** [UI] Get user URL
* **Step 5** [UI] Get user ID
* **Step 6** [API] Create a wall post using API with random text and get *Post ID* from API response
* **Step 7** [UI] Check the wall post is displayed
* **Step 8** [UI] Log out
* **Step 9** [UI] Go to VK website
* **Step 10** [UI] Go to user page via URL (*Step 4*)
* **Step 11** [UI] Check the wall post is displayed
* **Step 12** [UI] Check the quick login form is displayed
* **Post Condition** Delete created wall post
***
# TestCase004
* **Step 1** [UI] Go to VK website
* **Step 2** [UI] Log In
* **Step 3** [UI] Go to 'My Profile' page
* **Step 4** [API] Create a wall post using API with random text and get Post ID from API response
* **Step 5** [UI] Check the wall post is displayed
* **Step 6** [API] Edit the wall post using API - edit post text and add text file
* **Step 7** [UI] Check the wall post is edited
* **Step 8** [API] Add a comment to the wall post using API
* **Step 9** [UI] Check the comment is added
* **Step 10** [UI] Like the wall post
* **Step 11** [API] Check the wall post is liked using API
* **Step 12** [API] Delete the wall post using API
* **Step 13** [UI] Check the wall post is deleted
* **Post Condition** Delete uploaded text file
***
# TestCase005
* **Step 1** [UI] Go to VK website
* **Step 2** [UI] Log In
* **Step 3** [UI] Go to 'My Profile' page
* **Step 4** [API] Create a wall post using API with random text and image (from test data). Get Post ID from API response
* **Step 5** [UI] Check the wall post is displayed
* **Post Condition** Delete created wall post
# JSON configs
## config.json
### startURL
Start page for the tests.
### browser
*Chrome* or *Firefox*.
### timeout
Default value for the *Implicitly Wait* parameter.
### pollingTime
Period in *milliseconds*.The system checks every *%pollingTime%* for the downloaded file.
### downloadTimeout
Time in *seconds*. If file is not downloaded in *%downloadTimeout%* the case will Fail.
***
## browser.json
Configs for *Chrome* and *Firefox* browsers.
***
## apiConfig.json
### baseURI
Base url for the VK API.
### apiVersion
Version of VK API.
# Logs
Location -> */logs/*.  
XML config -> *src/main/resources/log4j2.xml*.
