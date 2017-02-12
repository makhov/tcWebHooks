package webhook.teamcity.payload.template.render;

import org.junit.Test;
import webhook.teamcity.payload.template.render.WebHookStringRenderer.WebHookHtmlRendererException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class WwwFormUrlEncodedToHtmlPrettyPrintingRendererTest {

    @Test
    public void testRender() throws UnsupportedEncodingException, WebHookHtmlRendererException {
        String line = URLEncoder.encode("name1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8") + "&" +
                URLEncoder.encode("name2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");
        WebHookStringRenderer renderer = new WwwFormUrlEncodedToHtmlPrettyPrintingRenderer();
        String result = renderer.render(line);
        System.out.println(result);
    }

    @Test
    public void testDecode() throws UnsupportedEncodingException, WebHookHtmlRendererException {
        String line = URLEncoder.encode("name1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8") + "&" +
                URLEncoder.encode("name2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8") + "&" +
                URLEncoder.encode("name0", "UTF-8") + "=" + URLEncoder.encode("value0", "UTF-8");
        WwwFormUrlEncodedToHtmlPrettyPrintingRenderer renderer = new WwwFormUrlEncodedToHtmlPrettyPrintingRenderer();
        Map<String, String> decoded = renderer.extractKeyValues(line);

        assertEquals("Decoded map should only contain 3 items", 3, decoded.size());
        assertEquals("Because a treeMap sorts keys, the second key should be name1", "name1", decoded.keySet().toArray()[1]);
        assertEquals("Because a treeMap sorts keys, third key should be name2", "name2", decoded.keySet().toArray()[2]);
    }

    @Test
    public void testDecodeLarge() throws UnsupportedEncodingException, WebHookHtmlRendererException {
        String line = "&buildFullName=TestTopProject+%3A%3A+TestMiddleProject+%3A%3A+SubProject+%3A%3A+TestBuild01&buildComment=null&changes=%5B%5D&extraParameters=%7Bbbb_param%3DTeamCity+version+10.0+EAP+%28build+40941%29+running+as+user+makhov%2C+body.failed%3D%7B++%22text%22%3A+%22Boo%21+Failed%22%2C+%22attachments%22%3A+%5B+%7B+%22fallback%22%3A+%22TestBuild01+%3Chttp%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%7Cbuild+%23137%3E+triggered+by+makhov+has+a+status+of+failure%22%2C+%22text%22%3A+%22TestBuild01+%3Chttp%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%7Cbuild+%23137%3E+triggered+by+makhov+has+a+status+of+failure%22%2C+%22color%22%3A+%22danger%22+%7D+%5D+%7D%2C+body.passed%3D%7B++%22text%22%3A+%22Yey%21+Passed%22%2C+%22attachments%22%3A+%5B+%7B+%22fallback%22%3A+%22TestBuild01+%3Chttp%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%7Cbuild+%23137%3E+triggered+by+makhov+has+a+status+of+failure%22%2C+%22text%22%3A+%22TestBuild01+%3Chttp%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%7Cbuild+%23137%3E+triggered+by+makhov+has+a+status+of+failure%22%2C+%22color%22%3A+%22good%22+%7D+%5D+%7D%2C+preferredDateFormat%3D%2C+project%3DSubProject%2C+revision%3Dsomething_In_Here%2C+test.thing%3DTesting+yeah+to+two+too+-+makhov%7D&agentOs=Linux%2C+version+3.11.0-15-generic&buildNumber=137&branchIsDefault=null&buildResultPrevious=failure&buildStatus=Exit+code+1&projectInternalId=project3&buildStatusUrl=http%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252&buildTypeId=TestTopProject_SubProject_TestBuild01&responsibilityUserNew=null&allParameters=%7Bwebhook%3D%7Bbbb_param%3DTeamCity+version+%24%7Bsystem.teamcity.version%7D+running+as+user+%24%7Benv.LOGNAME%7D%2C+body.failed%3D%7B++%22text%22%3A+%22Boo%21+Failed%22%2C+%22attachments%22%3A+%5B+%7B+%22fallback%22%3A+%22%24%7BbuildName%7D+%3C%24%7BbuildStatusUrl%7D%7Cbuild+%23%24%7BbuildNumber%7D%3E+triggered+by+%24%7BtriggeredBy%7D+has+a+status+of+%24%7BbuildResult%7D%22%2C+%22text%22%3A+%22%24%7BbuildName%7D+%3C%24%7BbuildStatusUrl%7D%7Cbuild+%23%24%7BbuildNumber%7D%3E+triggered+by+%24%7BtriggeredBy%7D+has+a+status+of+%24%7BbuildResult%7D%22%2C+%22color%22%3A+%22danger%22+%7D+%5D+%7D%2C+body.passed%3D%7B++%22text%22%3A+%22Yey%21+Passed%22%2C+%22attachments%22%3A+%5B+%7B+%22fallback%22%3A+%22%24%7BbuildName%7D+%3C%24%7BbuildStatusUrl%7D%7Cbuild+%23%24%7BbuildNumber%7D%3E+triggered+by+%24%7BtriggeredBy%7D+has+a+status+of+%24%7BbuildResult%7D%22%2C+%22text%22%3A+%22%24%7BbuildName%7D+%3C%24%7BbuildStatusUrl%7D%7Cbuild+%23%24%7BbuildNumber%7D%3E+triggered+by+%24%7BtriggeredBy%7D+has+a+status+of+%24%7BbuildResult%7D%22%2C+%22color%22%3A+%22good%22+%7D+%5D+%7D%2C+preferredDateFormat%3D%2C+project%3DSubProject%2C+revision%3Dsomething_In_Here%2C+test.thing%3DTesting+yeah+to+two+too+-+%24%7Benv.LOGNAME%7D%7D%2C+teamcity%3D%7Bbuild.counter%3D137%2C+build.number%3D137%2C+config%3D1.2.3.4%2C+env.BUILD_NUMBER%3D137%2C+env.GEM_HOME%3D%2Fhome%2Fnetwolfuk%2F.rvm%2Fgems%2Fruby-1.9.3-p545%2C+env.GEM_PATH%3D%2Fhome%2Fnetwolfuk%2F.rvm%2Fgems%2Fruby-1.9.3-p545%3A%2Fhome%2Fnetwolfuk%2F.rvm%2Fgems%2Fruby-1.9.3-p545%40global%2C+env.HOME%3D%2Fhome%2Fnetwolfuk%2C+env.IRBRC%3D%2Fhome%2Fnetwolfuk%2F.rvm%2Frubies%2Fruby-1.9.3-p545%2F.irbrc%2C+env.JAVA_HOME%3D%2Fopt%2FJava%2Fjdk1.8.0_65%2C+env.JDK_16%3D%2Fopt%2Fjava%2Fjdk1.6.0_33%2C+env.JDK_18%3D%2Fopt%2FJava%2Fjdk1.8.0_65%2C+env.JDK_18_x64%3D%2Fopt%2FJava%2Fjdk1.8.0_65%2C+env.JDK_HOME%3D%2Fopt%2FJava%2Fjdk1.8.0_65%2C+env.JRE_HOME%3D%2Fopt%2FJava%2Fjdk1.8.0_65%2C+env.LANG%3Den_NZ.UTF-8%2C+env.LANGUAGE%3Den_NZ%3Aen%2C+env.LESSCLOSE%3D%2Fusr%2Fbin%2Flesspipe+%25s+%25s%2C+env.LESSOPEN%3D%7C+%2Fusr%2Fbin%2Flesspipe+%25s%2C+env.LOGNAME%3Dnetwolfuk%2C+env.LS_COLORS%3Drs%3D0%3Adi%3D01%3B34%3Aln%3D01%3B36%3Amh%3D00%3Api%3D40%3B33%3Aso%3D01%3B35%3Ado%3D01%3B35%3Abd%3D40%3B33%3B01%3Acd%3D40%3B33%3B01%3Aor%3D40%3B31%3B01%3Asu%3D37%3B41%3Asg%3D30%3B43%3Aca%3D30%3B41%3Atw%3D30%3B42%3Aow%3D34%3B42%3Ast%3D37%3B44%3Aex%3D01%3B32%3A*.tar%3D01%3B31%3A*.tgz%3D01%3B31%3A*.arj%3D01%3B31%3A*.taz%3D01%3B31%3A*.lzh%3D01%3B31%3A*.lzma%3D01%3B31%3A*.tlz%3D01%3B31%3A*.txz%3D01%3B31%3A*.zip%3D01%3B31%3A*.z%3D01%3B31%3A*.Z%3D01%3B31%3A*.dz%3D01%3B31%3A*.gz%3D01%3B31%3A*.lz%3D01%3B31%3A*.xz%3D01%3B31%3A*.bz2%3D01%3B31%3A*.bz%3D01%3B31%3A*.tbz%3D01%3B31%3A*.tbz2%3D01%3B31%3A*.tz%3D01%3B31%3A*.deb%3D01%3B31%3A*.rpm%3D01%3B31%3A*.jar%3D01%3B31%3A*.war%3D01%3B31%3A*.ear%3D01%3B31%3A*.sar%3D01%3B31%3A*.rar%3D01%3B31%3A*.ace%3D01%3B31%3A*.zoo%3D01%3B31%3A*.cpio%3D01%3B31%3A*.7z%3D01%3B31%3A*.rz%3D01%3B31%3A*.jpg%3D01%3B35%3A*.jpeg%3D01%3B35%3A*.gif%3D01%3B35%3A*.bmp%3D01%3B35%3A*.pbm%3D01%3B35%3A*.pgm%3D01%3B35%3A*.ppm%3D01%3B35%3A*.tga%3D01%3B35%3A*.xbm%3D01%3B35%3A*.xpm%3D01%3B35%3A*.tif%3D01%3B35%3A*.tiff%3D01%3B35%3A*.png%3D01%3B35%3A*.svg%3D01%3B35%3A*.svgz%3D01%3B35%3A*.mng%3D01%3B35%3A*.pcx%3D01%3B35%3A*.mov%3D01%3B35%3A*.mpg%3D01%3B35%3A*.mpeg%3D01%3B35%3A*.m2v%3D01%3B35%3A*.mkv%3D01%3B35%3A*.webm%3D01%3B35%3A*.ogm%3D01%3B35%3A*.mp4%3D01%3B35%3A*.m4v%3D01%3B35%3A*.mp4v%3D01%3B35%3A*.vob%3D01%3B35%3A*.qt%3D01%3B35%3A*.nuv%3D01%3B35%3A*.wmv%3D01%3B35%3A*.asf%3D01%3B35%3A*.rm%3D01%3B35%3A*.rmvb%3D01%3B35%3A*.flc%3D01%3B35%3A*.avi%3D01%3B35%3A*.fli%3D01%3B35%3A*.flv%3D01%3B35%3A*.gl%3D01%3B35%3A*.dl%3D01%3B35%3A*.xcf%3D01%3B35%3A*.xwd%3D01%3B35%3A*.yuv%3D01%3B35%3A*.cgm%3D01%3B35%3A*.emf%3D01%3B35%3A*.axv%3D01%3B35%3A*.anx%3D01%3B35%3A*.ogv%3D01%3B35%3A*.ogx%3D01%3B35%3A*.aac%3D00%3B36%3A*.au%3D00%3B36%3A*.flac%3D00%3B36%3A*.mid%3D00%3B36%3A*.midi%3D00%3B36%3A*.mka%3D00%3B36%3A*.mp3%3D00%3B36%3A*.mpc%3D00%3B36%3A*.ogg%3D00%3B36%3A*.ra%3D00%3B36%3A*.wav%3D00%3B36%3A*.axa%3D00%3B36%3A*.oga%3D00%3B36%3A*.spx%3D00%3B36%3A*.xspf%3D00%3B36%3A%2C+env.MAIL%3D%2Fvar%2Fmail%2Fnetwolfuk%2C+env.MY_RUBY_HOME%3D%2Fhome%2Fnetwolfuk%2F.rvm%2Frubies%2Fruby-1.9.3-p545%2C+env.NLSPATH%3D%2Fusr%2Fdt%2Flib%2Fnls%2Fmsg%2F%25L%2F%25N.cat%2C+env.NODE_PATH%3D%2Fusr%2Flib%2Fnodejs%3A%2Fusr%2Flib%2Fnode_modules%3A%2Fusr%2Fshare%2Fjavascript%2C+env.OLDPWD%3D%2Fopt%2FTeamCity%2Fbin%2C+env.PATH%3D%2Fhome%2Fnetwolfuk%2F.rvm%2Fgems%2Fruby-1.9.3-p545%2Fbin%3A%2Fhome%2Fnetwolfuk%2F.rvm%2Fgems%2Fruby-1.9.3-p545%40global%2Fbin%3A%2Fhome%2Fnetwolfuk%2F.rvm%2Frubies%2Fruby-1.9.3-p545%2Fbin%3A%2Fhome%2Fnetwolfuk%2Fbin%3A%2Fopt%2FJava%2Fjdk1.8.0_65%2Fbin%3A%2Fusr%2Flocal%2Fsbin%3A%2Fusr%2Flocal%2Fbin%3A%2Fusr%2Fsbin%3A%2Fusr%2Fbin%3A%2Fsbin%3A%2Fbin%3A%2Fusr%2Fgames%3A%2Fhome%2Fnetwolfuk%2F.rvm%2Fbin%2C+env.PWD%3D%2Fopt%2FTeamCity%2FbuildAgent%2Fbin%2C+env.SHELL%3D%2Fbin%2Fbash%2C+env.SHLVL%3D1%2C+env.SSH_CLIENT%3D192.168.1.20+57467+22%2C+env.SSH_CONNECTION%3D192.168.1.20+57467+192.168.1.72+22%2C+env.SSH_TTY%3D%2Fdev%2Fpts%2F0%2C+env.TEAMCITY_BUILDCONF_NAME%3DTestBuild01%2C+env.TEAMCITY_BUILD_PROPERTIES_FILE%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2Fteamcity.build3307031608884593871.properties%2C+env.TEAMCITY_CAPTURE_ENV%3D%22%2Fopt%2FJava%2Fjdk1.8.0_65%2Fjre%2Fbin%2Fjava%22+-jar+%22%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Fplugins%2Fenvironment-fetcher%2Fbin%2Fenv-fetcher.jar%22%2C+env.TEAMCITY_GIT_PATH%3D%2Fusr%2Fbin%2Fgit%2C+env.TEAMCITY_PROJECT_NAME%3DSubProject%2C+env.TEAMCITY_SERVER_MEM_OPTS%3D-Xmx512m+-XX%3AMaxPermSize%3D270m+-Xdebug+-Xrunjdwp%3Atransport%3Ddt_socket%2Caddress%3D8000%2Cserver%3Dy%2Csuspend%3Dn%2C+env.TEAMCITY_VERSION%3D10.0+EAP+%28build+40941%29%2C+env.TEMP%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2C+env.TERM%3Dxterm%2C+env.TMP%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2C+env.TMPDIR%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2C+env.USER%3Dnetwolfuk%2C+env.XDG_SESSION_COOKIE%3D5363a53ca15ab152ce5c5b170000018a-1467338198.126316-1963695377%2C+env.XFILESEARCHPATH%3D%2Fusr%2Fdt%2Fapp-defaults%2F%25L%2FDt%2C+env._%3D..%2FbuildAgent%2Fbin%2Fagent.sh%2C+env._system_arch%3Dx86_64%2C+env._system_name%3DUbuntu%2C+env._system_type%3DLinux%2C+env._system_version%3D12.04%2C+env.rvm_bin_path%3D%2Fhome%2Fnetwolfuk%2F.rvm%2Fbin%2C+env.rvm_path%3D%2Fhome%2Fnetwolfuk%2F.rvm%2C+env.rvm_prefix%3D%2Fhome%2Fnetwolfuk%2C+env.rvm_version%3D1.25.22+%28stable%29%2C+env.teamcity.distribution%3D%2Fopt%2FTeamCity%2C+rvm.rubies.list%3Druby-1.9.3-p545%2C+system.agent.home.dir%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2C+system.agent.name%3DDefault+Agent%2C+system.agent.work.dir%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Fwork%2C+system.build.number%3D137%2C+system.build.vcs.number%3Dsomething_In_Here%2C+system.java.io.tmpdir%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2C+system.jdk6%3D%2Fopt%2Fjava%2Fjdk1.6.0_33%2C+system.jdk7%3D%2Fopt%2Fjava%2Fjdk1.7.0_51%2C+system.jdk8%3D%2Fopt%2Fjava%2Fjdk1.8.0_65%2C+system.path.macro.TeamCityDistribution%3D%2Fopt%2FTeamCity%2C+system.teamcity.agent.cpuBenchmark%3D609%2C+system.teamcity.agent.dotnet.agent_url%3Dhttp%3A%2F%2Flocalhost%3A9090%2FRPC2%2C+system.teamcity.agent.dotnet.build_id%3D1252%2C+system.teamcity.auth.password%3DMdewD6gLnO9Qg8yqK0OErhCm1ApiUUMV%2C+system.teamcity.auth.userId%3DTeamCityBuildId%3D1252%2C+system.teamcity.build.changedFiles.file%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2FchangedFiles6083542298713806826.txt%2C+system.teamcity.build.checkoutDir%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Fwork%2F3de96e708f2408e%2C+system.teamcity.build.properties.file%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2Fteamcity.build3307031608884593871.properties%2C+system.teamcity.build.tempDir%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2C+system.teamcity.buildConfName%3DTestBuild01%2C+system.teamcity.buildType.id%3DTestTopProject_SubProject_TestBuild01%2C+system.teamcity.configuration.properties.file%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2Fteamcity.config8267813528965781866.properties%2C+system.teamcity.projectName%3DSubProject%2C+system.teamcity.runner.properties.file%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2Fteamcity.runner4155265354843369527.properties%2C+system.teamcity.tests.recentlyFailedTests.file%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftemp%2FbuildTmp%2FtestsToRunFirst1778310155860681869.txt%2C+system.teamcity.version%3D10.0+EAP+%28build+40941%29%2C+teamcity.agent.hardware.cpuCount%3D2%2C+teamcity.agent.hardware.memorySizeMb%3D2001%2C+teamcity.agent.home.dir%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2C+teamcity.agent.hostname%3Dubuntu-teamcity-01%2C+teamcity.agent.jvm.file.encoding%3DUTF-8%2C+teamcity.agent.jvm.file.separator%3D%2F%2C+teamcity.agent.jvm.os.arch%3Damd64%2C+teamcity.agent.jvm.os.name%3DLinux%2C+teamcity.agent.jvm.os.version%3D3.11.0-15-generic%2C+teamcity.agent.jvm.path.separator%3D%3A%2C+teamcity.agent.jvm.specification%3D1.8%2C+teamcity.agent.jvm.user.country%3DNZ%2C+teamcity.agent.jvm.user.home%3D%2Fhome%2Fnetwolfuk%2C+teamcity.agent.jvm.user.language%3Den%2C+teamcity.agent.jvm.user.name%3Dnetwolfuk%2C+teamcity.agent.jvm.user.timezone%3DPacific%2FAuckland%2C+teamcity.agent.jvm.version%3D1.8.0_65%2C+teamcity.agent.launcher.version%3DNo+matifest+file+in+jar%2C+teamcity.agent.name%3DDefault+Agent%2C+teamcity.agent.ownPort%3D9090%2C+teamcity.agent.protocol%3Dxml-rpc%2C+teamcity.agent.tools.dir%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2C+teamcity.agent.work.dir%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Fwork%2C+teamcity.agent.work.dir.freeSpaceMb%3D20325%2C+teamcity.build.checkoutDir%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Fwork%2F3de96e708f2408e%2C+teamcity.build.default.checkoutDir%3D3de96e708f2408e%2C+teamcity.build.id%3D1252%2C+teamcity.build.triggeredBy%3Dnetwolfuk%2C+teamcity.build.triggeredBy.username%3Dnetwolfuk%2C+teamcity.dotCover.home%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2FdotCover%2C+teamcity.project.id%3DTestTopProject_SubProject%2C+teamcity.serverUrl%3Dhttp%3A%2F%2Flocalhost%3A8111%2C+teamcity.storage.type%3DDefaultStorage%2C+teamcity.tool.ant-net-tasks%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fant-net-tasks%2C+teamcity.tool.dotCover%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2FdotCover%2C+teamcity.tool.gant%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fgant%2C+teamcity.tool.idea%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fidea%2C+teamcity.tool.jacoco%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fjacoco%2C+teamcity.tool.jps%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fjps%2C+teamcity.tool.jps-old%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fjps-old%2C+teamcity.tool.maven%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fmaven%2C+teamcity.tool.maven3%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fmaven3%2C+teamcity.tool.maven3_1%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fmaven3_1%2C+teamcity.tool.maven3_2%3D%2Fopt%2FTeamCity-10EAP1-40941%2FTeamCity%2FbuildAgent%2Ftools%2Fmaven3_2%2C+test.thing%3DThis+is+testing.+Yeah%2C+webhook.bbb_param%3DTeamCity+version+%24%7Bsystem.teamcity.version%7D+running+as+user+%24%7Benv.LOGNAME%7D%2C+webhook.body.failed%3D%7B++%22text%22%3A+%22Boo%21+Failed%22%2C+%22attachments%22%3A+%5B+%7B+%22fallback%22%3A+%22%24%7BbuildName%7D+%3C%24%7BbuildStatusUrl%7D%7Cbuild+%23%24%7BbuildNumber%7D%3E+triggered+by+%24%7BtriggeredBy%7D+has+a+status+of+%24%7BbuildResult%7D%22%2C+%22text%22%3A+%22%24%7BbuildName%7D+%3C%24%7BbuildStatusUrl%7D%7Cbuild+%23%24%7BbuildNumber%7D%3E+triggered+by+%24%7BtriggeredBy%7D+has+a+status+of+%24%7BbuildResult%7D%22%2C+%22color%22%3A+%22danger%22+%7D+%5D+%7D%2C+webhook.body.passed%3D%7B++%22text%22%3A+%22Yey%21+Passed%22%2C+%22attachments%22%3A+%5B+%7B+%22fallback%22%3A+%22%24%7BbuildName%7D+%3C%24%7BbuildStatusUrl%7D%7Cbuild+%23%24%7BbuildNumber%7D%3E+triggered+by+%24%7BtriggeredBy%7D+has+a+status+of+%24%7BbuildResult%7D%22%2C+%22text%22%3A+%22%24%7BbuildName%7D+%3C%24%7BbuildStatusUrl%7D%7Cbuild+%23%24%7BbuildNumber%7D%3E+triggered+by+%24%7BtriggeredBy%7D+has+a+status+of+%24%7BbuildResult%7D%22%2C+%22color%22%3A+%22good%22+%7D+%5D+%7D%2C+webhook.project%3DSubProject%2C+webhook.revision%3Dsomething_In_Here%2C+webhook.test.thing%3DTesting+yeah+to+two+too+-+%24%7Benv.LOGNAME%7D%7D%7D&buildRunners=%5BAnt%5D&buildStartTime=&buildTags=%5B%5D&responsibilityUserOld=null&buildFinishTime=null&buildStateDescription=finished&text=TestTopProject+%3A%3A+TestMiddleProject+%3A%3A+SubProject+%3A%3A+TestBuild01+has+finished.+Status%3A+failure&class=class+webhook.teamcity.payload.content.WebHookPayloadContent&buildName=TestBuild01&buildResult=failure&agentName=Default+Agent&branchName=null&buildResultDelta=unchanged&buildId=1252&message=Build+TestTopProject+%3A%3A+TestMiddleProject+%3A%3A+SubProject+%3A%3A+TestBuild01+has+finished.+This+is+build+number+137%2C+has+a+status+of+%22failure%22+and+was+triggered+by+makhov&buildExternalTypeId=TestTopProject_SubProject_TestBuild01&rootUrl=http%3A%2F%2Fubuntu-teamcity-01%3A8111%2F&currentTime=&notifyType=buildFinished&buildInternalTypeId=bt2&comment=null&projectExternalId=TestTopProject_SubProject&branchDisplayName=null&projectName=SubProject&projectId=TestTopProject_SubProject&agentHostname=localhost&buildStatusHtml=%3Cspan+class%3D%22tcWebHooksMessage%22%3E%3Ca+href%3D%22http%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2Fproject.html%3FprojectId%3DTestTopProject_SubProject%22%3ESubProject%3C%2Fa%3E+%3A%3A+%3Ca+href%3D%22http%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewType.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%22%3ETestBuild01%3C%2Fa%3E+%23+%3Ca+href%3D%22http%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%22%3E%3Cstrong%3E137%3C%2Fstrong%3E%3C%2Fa%3E+has+%3Cstrong%3Efinished%3C%2Fstrong%3E+with+a+status+of+%3Ca+href%3D%22http%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%22%3E+%3Cstrong%3Efailure%3C%2Fstrong%3E%3C%2Fa%3E+and+was+triggered+by+%3Cstrong%3Enetwolfuk%3C%2Fstrong%3E%3C%2Fspan%3E&triggeredBy=makhov&bbb_param=TeamCity+version+10.0+EAP+%28build+40941%29+running+as+user+makhov&body.failed=%7B++%22text%22%3A+%22Boo%21+Failed%22%2C+%22attachments%22%3A+%5B+%7B+%22fallback%22%3A+%22TestBuild01+%3Chttp%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%7Cbuild+%23137%3E+triggered+by+makhov+has+a+status+of+failure%22%2C+%22text%22%3A+%22TestBuild01+%3Chttp%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%7Cbuild+%23137%3E+triggered+by+makhov+has+a+status+of+failure%22%2C+%22color%22%3A+%22danger%22+%7D+%5D+%7D&body.passed=%7B++%22text%22%3A+%22Yey%21+Passed%22%2C+%22attachments%22%3A+%5B+%7B+%22fallback%22%3A+%22TestBuild01+%3Chttp%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%7Cbuild+%23137%3E+triggered+by+makhov+has+a+status+of+failure%22%2C+%22text%22%3A+%22TestBuild01+%3Chttp%3A%2F%2Fubuntu-teamcity-01%3A8111%2F%2FviewLog.html%3FbuildTypeId%3DTestTopProject_SubProject_TestBuild01%26buildId%3D1252%7Cbuild+%23137%3E+triggered+by+makhov+has+a+status+of+failure%22%2C+%22color%22%3A+%22good%22+%7D+%5D+%7D&preferredDateFormat=&project=SubProject&revision=something_In_Here&test.thing=Testing+yeah+to+two+too+-+makhov ";
        WwwFormUrlEncodedToHtmlPrettyPrintingRenderer renderer = new WwwFormUrlEncodedToHtmlPrettyPrintingRenderer();
        Map<String, String> decoded = renderer.extractKeyValues(line);

        assertEquals("Decoded map should only contain 50 items", 50, decoded.size());
    }

}
