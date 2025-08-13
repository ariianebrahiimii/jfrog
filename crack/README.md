# artifactory-injector

# Usage

```$ java -jar artifactory-injector-1.1.jar```

# Contributing

If you're reading this, you're awesome! Here are a few guidelines that will help you along the way.

1. Fork this repository
2. Clone your fork to your local machine `git clone git@github.com:<yourname>/artifactory-injector.git`
3. Create a branch `git checkout -b my-topic-branch`
4. Make your changes, then push to GitHub with `git push --set-upstream origin my-topic-branch`.
5. Visit GitHub and make your pull request.


## Testing

Testing is difficult due to needed compilation of injecting classes.

Currently you have to run `mvn package` and then execute jar file `java -jar target/artifactory-injector-1.1.jar`