#
# Cookbook Name:: scala-web
# Recipe:: default
#

include_recipe "java"

case node['platform_family']
when 'debian'
  execute '' do
    command 'echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list'
  end

  execute '' do
    command 'sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823'
  end

  execute '' do
    command 'sudo apt-get update'
  end

when 'rhel'
  execute '' do
    command 'curl https://bintray.com/sbt/rpm/rpm > bintray-sbt-rpm.repo'
  end

  execute '' do
    command 'sudo mv bintray-sbt-rpm.repo /etc/yum.repos.d/'
  end
end

package 'sbt' do
  action :install
end
