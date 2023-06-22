# Cleema Android

### Setting things up
Run the following commands in your Terminal:
```
bundle install
brew bundle
```

### Pre-Compile
The project uses a `secret.properties` and a `keystore.properties` file for API tokens etc. which must be copied to the projects root folder.

### Launch arguments for debugging
- set ```--ez wipeuser true``` to remove the currently logged in user in "Edit Configurations" of the **app** module under **Launch Flags** in **Launch Options**.
