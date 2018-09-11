cd /D "DIRECTORY"

PAUSE

git checkout trunk
git pull blocklings trunk

git checkout branch
git merge trunk

PAUSE

git checkout trunk
git merge branch

PAUSE

git push blocklings branch
git push blocklings trunk

PAUSE