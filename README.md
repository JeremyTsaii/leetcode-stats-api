<h2 align="center">leetcode-stats-api</h2>
<h3 align="center">The API for retrieving your LeetCode profile statistics</h3>

<h3 align="center">Check it out <a href="https://leetcode-stats-api.herokuapp.com/">here</a>!</h3>

<p align="center">
  <a href=https://forthebadge.com>
    <img src="https://forthebadge.com/images/badges/built-with-grammas-recipe.svg"></a>
  </br>
  <a href="https://leetcode-stats-api.herokuapp.com/">
    <img src="https://pyheroku-badge.herokuapp.com/?app=leetcode-stats-api"></a>
  <a href=https://travis-ci.com/JeremyTsaii/leetcode-stats-api>
    <img src="https://travis-ci.com/JeremyTsaii/leetcode-stats-api.svg?branch=master"></a>
  <a href=http://hits.dwyl.com/jeremytsaii/leetcode-stats-api>
    <img alt="HitCount" src=http://hits.dwyl.com/jeremytsaii/leetcode-stats-api.svg></a>
  <a href=https://github.com/dwyl/esta/issues>
    <img src="https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat"></a>
  <a href=https://opensource.org/licenses/MIT>
    <img src=https://img.shields.io/badge/License-MIT-yellow.svg></a>
</p>

***

### REST API Endpoint

Invoke URL: https://leetcode-stats-api.herokuapp.com/{YOUR_USERNAME}

#### GET:
Hitting the endpoint with your username returns the following statistics in the json response:
```
{
  "status": "",
  "totalSolved": 360
  "easySolved": 146
  "totalEasy": 458
  "mediumSolved": 196
  "totalMedium": 904
  "hardSolved": 21
  "totalHard": 368
  "acceptanceRate": 50.9
  "ranking": 47657
  "contributionPoints": 2534
  "reputation": 1
}
```

