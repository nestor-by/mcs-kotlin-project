rootProject.name = "mcs-kotlin-project"

include(
    ":graphql-test-jooq",
    ":graphql-test-mongo",
    ":graphql-test-server",
    ":graphql-test-client",
    ":component:graphql-core",
    ":component:graphql-dataloader",
    ":component:graphql-mongodb",
    ":component:graphql-jooq",
    ":component:graphql-apollo",
    ":component:launcher-netty"
)