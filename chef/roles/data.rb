name 'data'
description 'data'

default_attributes(
  # 'postgresql' => {
  #   'password' => {
  #     'postgres' => 'password'# This should be the md5 hash of the password
  #   },
  #   'config' => {
  #     'listen_addresses' => '192.168.41.10,192.168.41.20'
  #   },
  #   'pg_hba' => [
  #     { type: 'host', db: 'all', user: 'postgres', addr: '192.168.41.20/32', method: 'md5'},
  #     { type: 'host', db: 'all', user: 'postgres', addr: '192.168.41.10/32', method: 'md5'}
  #   ]
  # }
)

run_list(
    'recipe[sc-mongodb::default]',
    #'recipe[postgresql::server]'
)
