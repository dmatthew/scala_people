name 'web'
description 'web'

default_attributes(
  'java' => {
    'jdk_version' => '8'
  }
)

run_list(
    'recipe[scala-web::default]',
    #'recipe[scala::default]', # To install scala without sbt
    'recipe[postgresql::client]'
)
