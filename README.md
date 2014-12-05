hashrecommender
==============

Recomends hashtags based on previous tweets with hashtags.
It only works with users in the data base. 

Next goal: Work with all the users in Twitter.

BDML Project for http://aprendizajengrande.net

Inspired in gitrecommender: https://github.com/DrDub/gitrecommender/

Current usage
-------------
<pre>
$ ./hashrecommender user_name path/to/ids.txt path/to/hashtags_ids.txt path/to/hashtags_per_user.txt
</pre>

An example...
-------------
<pre>
UserBased Recommendation based on Twitter Hashtags
	-----------------------
	Searching recommendations for: lthurr
	The user lthurr has the following tweets
	['resfriodetomi', 'algund', 'unc', 'hackatong', 'storify', 'alsicebucketchallenge', 'amyleeaftermath', 'benedetti', 'elnietodeestela', 'rockinrio2015', 'preguntados']
	Recommendations:
	> edutech - value: 0.055
	> arg - value: 0.04
	> semanatic - value: 0.024999999
	> programar - value: 0.02
	> startup - value: 0.015
	> startups - value: 0.015
	> aulica - value: 0.015
	> rse - value: 0.015
	> selfie - value: 0.01
	> python - value: 0.01
	-----------------------
</pre>

Need more data?
--------------
Please, you can use my TweetDataCollector client.
https://github.com/lthurr/TwDataCollector
