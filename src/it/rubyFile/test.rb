$log.info "hello ruby from file"
$log.info $project.name
$log.info $project.version
$log.info $project.basedir
$log.info $project.build.finalName
$log.info $project.build.directory
$log.info $project.build.outputDirectory
$log.info $settings.localRepository
$log.info $mvn.session.executionRootDirectory
$log.info $mvn.scriptFile
p $0
p $mvn.arguments
p ARGV
