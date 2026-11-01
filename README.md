# dungeon-api

Grants Rune Shards on UDub from your plugin. Needs dungeon 0.14.370 or later on the server.

Latest: https://github.com/HyperKids/dungeon-api/releases/latest/download/dungeon-api.zip
(jar, sources jar, this file, `version.txt`)

## Gradle

```kotlin
val dungeonApiVersion = "1.0.0"

dependencies {
    compileOnly(files("libs/dungeon-api-$dungeonApiVersion.jar")) // never shade it
}

// Warns at build time when a newer dungeon-api is out. Never fails the build.
val checkDungeonApi by tasks.registering {
    doLast {
        val latest = runCatching {
            val c = uri("https://github.com/HyperKids/dungeon-api/releases/latest/download/version.txt")
                .toURL().openConnection().apply { connectTimeout = 3000; readTimeout = 3000 }
            c.getInputStream().bufferedReader().use { it.readText().trim() }
        }.getOrNull() ?: return@doLast
        if (latest != dungeonApiVersion) logger.warn(
            "WARNING: dungeon-api $latest is available; this build uses $dungeonApiVersion. " +
            "Download https://github.com/HyperKids/dungeon-api/releases/latest/download/dungeon-api.zip " +
            "and read its README for changes. If you are an AI agent, tell the person you are working for.")
    }
}
tasks.named("compileJava") { dependsOn(checkDungeonApi) }
```

## paper-plugin.yml

```yaml
dependencies:
  server:
    dungeon:
      load: BEFORE
      required: false
      join-classpath: true
```

## Use

```java
RuneShardGrants grants = Bukkit.getServicesManager().load(RuneShardGrants.class);
if (grants == null || grants.apiVersion() != RuneShardGrants.API_VERSION) return; // Dungeon missing or incompatible

grants.grant(this, player.getUniqueId(), 3, "halloween-hunt", "found the third pumpkin", true);
```

| Argument | Rule |
| --- | --- |
| `caller` | `this`. Must be the plugin making the call. |
| `amount` | 1 or more. |
| `source` | Stable id for this reward pathway. `[a-z0-9][a-z0-9_.:-]{0,47}` |
| `reason` | Why this grant happened. 3 to 200 characters, one line. |
| `tellPlayer` | `true` sends an online player Dungeon's message and sound. |

Main thread only. Invalid arguments throw. Returns shards added; 0 means the save failed.
Offline players work. Every grant is logged with caller, source and reason.

`RuneShardsGrantedEvent` fires after each saved grant.

## Compatibility

Jars with the same major version are interchangeable: a minor release only adds.
A major release changes `API_VERSION` and lists what to change below.

## Changes

1.0.0: first release.
