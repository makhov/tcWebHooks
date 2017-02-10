package webhook.testframework;

import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.settings.ProjectSettingsManager;
import org.jdom.JDOMException;
import webhook.WebHook;
import webhook.WebHookImpl;
import webhook.teamcity.*;
import webhook.teamcity.auth.UsernamePasswordAuthenticatorFactory;
import webhook.teamcity.auth.WebHookAuthenticatorProvider;
import webhook.teamcity.payload.*;
import webhook.teamcity.payload.content.ExtraParametersMap;
import webhook.teamcity.payload.content.WebHookPayloadContent;
import webhook.teamcity.payload.format.WebHookPayloadJson;
import webhook.teamcity.payload.format.WebHookPayloadJsonTemplate;
import webhook.teamcity.payload.format.WebHookPayloadNameValuePairs;
import webhook.teamcity.payload.format.WebHookPayloadXml;
import webhook.teamcity.settings.WebHookConfig;
import webhook.teamcity.settings.WebHookMainSettings;
import webhook.teamcity.settings.WebHookProjectSettings;
import webhook.testframework.util.ConfigLoaderUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.*;

public class WebHookMockingFrameworkImpl implements WebHookMockingFramework {

    WebHookPayloadContent content;
    WebHookConfig webHookConfig;
    SBuildServer sBuildServer = mock(SBuildServer.class);
    BuildHistory buildHistory = mock(BuildHistory.class);
    ProjectSettingsManager settings = mock(ProjectSettingsManager.class);
    ProjectManager projectManager = mock(ProjectManager.class);
    WebHookMainSettings configSettings = mock(WebHookMainSettings.class);
    WebHookPayloadManager manager = mock(WebHookPayloadManager.class);
    WebHookTemplateResolver resolver = mock(WebHookTemplateResolver.class);
    WebHookTemplateManager templateManager = mock(WebHookTemplateManager.class);
    //WebHookContentBuilder contentBuilder = mock(WebHookContentBuilder.class);
    WebHookContentBuilder contentBuilder = new WebHookContentBuilder(getServer(), manager, resolver);
    WebHookTemplate template;
    WebHookPayload payloadJson = new WebHookPayloadJson(manager);
    WebHookPayload payloadXml = new WebHookPayloadXml(manager);
    WebHookPayload payloadNvpairs = new WebHookPayloadNameValuePairs(manager);
    WebHookPayload payloadJsonTemplate = new WebHookPayloadJsonTemplate(manager);
    WebHookAuthenticatorProvider authenticatorProvider = new WebHookAuthenticatorProvider();
    WebHookPayload payload = new WebHookPayloadJson(manager);
    WebHookProjectSettings projSettings;
    //WebHookFactory factory = mock(WebHookFactory.class);
    WebHookFactory factory = new WebHookFactoryImpl(configSettings, authenticatorProvider, new WebHookHttpClientFactoryImpl());
    WebHook webhook = mock(WebHook.class);
    WebHook webHookImpl;
    WebHook spyWebHook;
    SFinishedBuild previousSuccessfulBuild = mock(SFinishedBuild.class);
    SFinishedBuild previousFailedBuild = mock(SFinishedBuild.class);
    List<SFinishedBuild> finishedSuccessfulBuilds = new ArrayList<>();
    List<SFinishedBuild> finishedFailedBuilds = new ArrayList<>();
    List<SFinishedBuild> finishedBuildsHistory = new ArrayList<>();

    SBuildType sBuildType = new MockSBuildType("Test Build", "A Test Build", "bt1");
    SBuildType sBuildType02 = new MockSBuildType("Test Build-2", "A Test Build 02", "bt2");
    SBuildType sBuildType03 = new MockSBuildType("Test Build-2", "A Test Build 03", "bt3");
    SRunningBuild sRunningBuild = new MockSRunningBuild(sBuildType, "SubVersion", Status.NORMAL, "Running", "TestBuild01");
    SProject sProject = new MockSProject("Test Project", "A test project", "project01", "ATestProject", sBuildType);
    SProject sProject02 = new MockSProject("Test Project 02", "A test project 02", "project2", "TestProjectNumber02", sBuildType);
    SProject sProject03 = new MockSProject("Test Project 03", "A test sub project 03", "project3", "TestProjectNumber02_TestProjectNumber03", sBuildType);

    UsernamePasswordAuthenticatorFactory authenticatorFactory = new UsernamePasswordAuthenticatorFactory(authenticatorProvider);

    SBuildType build2 = mock(SBuildType.class);
    SBuildType build3 = mock(SBuildType.class);

    WebHookListener whl;
    SortedMap<String, String> extraParameters;
    SortedMap<String, String> teamcityProperties;
    BuildStateEnum buildstateEnum;
    List<WebHookTemplate> templateList = new ArrayList<>();
    List<WebHookPayload> formatList = new ArrayList<>();

    private WebHookMockingFrameworkImpl() {
        webHookImpl = new WebHookImpl();
        spyWebHook = spy(webHookImpl);
        whl = new WebHookListener(sBuildServer, settings, configSettings, manager, factory, resolver, contentBuilder);
        projSettings = new WebHookProjectSettings();
//		when(factory.getWebHook(webHookConfig,null)).thenReturn(webHookImpl);
//		when(factory.getWebHook()).thenReturn(webHookImpl);
//		when(factory.getWebHook(any(WebHookConfig.class), any(WebHookProxyConfig.class))).thenReturn(webHookImpl);
        when(manager.isRegisteredFormat("nvpairs")).thenReturn(true);
        when(manager.getFormat("nvpairs")).thenReturn(payloadNvpairs);
        when(manager.isRegisteredFormat("json")).thenReturn(true);
        when(manager.getFormat("json")).thenReturn(payloadJson);
        //when(factory.getWebHook()).thenReturn(spyWebHook);
//		when(factory.getWebHook()).thenReturn(webHookImpl);
        when(manager.isRegisteredFormat("JSON")).thenReturn(true);
        when(manager.getFormat("JSON")).thenReturn(payloadJson);
        when(manager.getServer()).thenReturn(sBuildServer);
        formatList.add(payloadJson);
        formatList.add(payloadXml);
        formatList.add(payloadNvpairs);
        formatList.add(payloadJsonTemplate);
        when(manager.getRegisteredFormats()).thenReturn(formatList);
        when(projectManager.findProjectById("project01")).thenReturn(sProject);
        when(sBuildServer.getHistory()).thenReturn(buildHistory);
        when(sBuildServer.getRootUrl()).thenReturn("http://test.server");
        when(sBuildServer.getProjectManager()).thenReturn(projectManager);
        when(previousSuccessfulBuild.getBuildStatus()).thenReturn(Status.NORMAL);
        when(previousSuccessfulBuild.isPersonal()).thenReturn(false);
        when(previousSuccessfulBuild.getFinishDate()).thenReturn(new Date());
        when(previousFailedBuild.getBuildStatus()).thenReturn(Status.FAILURE);
        when(previousFailedBuild.isPersonal()).thenReturn(false);
        when(previousFailedBuild.getFinishDate()).thenReturn(new Date());
        finishedSuccessfulBuilds.add(previousSuccessfulBuild);
        finishedFailedBuilds.add(previousFailedBuild);
        ((MockSBuildType) sBuildType).setProject(sProject);
        when(settings.getSettings(sRunningBuild.getProjectId(), "webhooks")).thenReturn(projSettings);

        when(build2.getBuildTypeId()).thenReturn("bt2");
        when(build2.getInternalId()).thenReturn("bt2");
        when(build2.getName()).thenReturn("This is Build 2");
        when(build3.getBuildTypeId()).thenReturn("bt3");
        when(build3.getInternalId()).thenReturn("bt3");
        when(build3.getName()).thenReturn("This is Build 3");
        ((MockSProject) sProject).addANewBuildTypeToTheMock(build2);
        ((MockSProject) sProject).addANewBuildTypeToTheMock(build3);
        ((MockSProject) sProject02).addANewBuildTypeToTheMock(sBuildType02);
        ((MockSProject) sProject03).addANewBuildTypeToTheMock(sBuildType03);
        ((MockSProject) sProject03).setParentProject(sProject02);
        ((MockSProject) sProject02).addChildProjectToMock(sProject03);
        whl.register();
        authenticatorFactory.register();
        template = getTestingTemplate();
        templateList.add(template);
        when(templateManager.getRegisteredTemplates()).thenReturn(templateList);
        when(resolver.findWebHookTemplatesForProject(sProject)).thenReturn(templateList);

        finishedBuildsHistory.addAll(finishedSuccessfulBuilds);
        finishedBuildsHistory.addAll(finishedFailedBuilds);

        ((MockSBuildType) sBuildType).setMockingFrameworkInstance(this);

    }

    @Override
    public List<SFinishedBuild> getMockedBuildHistory() {

        return finishedBuildsHistory;
    }

    private WebHookTemplate getTestingTemplate() {
        return new WebHookTemplate() {
            @SuppressWarnings("unused")
            WebHookTemplateManager manager = null;
            BuildStateEnum[] supportedStates = {BuildStateEnum.BUILD_SUCCESSFUL, BuildStateEnum.BUILD_FAILED, BuildStateEnum.BUILD_BROKEN, BuildStateEnum.BUILD_FIXED};

            @Override
            public boolean supportsPayloadFormat(String payloadFormat) {
                return true;
            }

            @Override
            public void setTemplateManager(WebHookTemplateManager webhookTemplateManager) {
                this.manager = webhookTemplateManager;
            }

            @Override
            public void setRank(Integer rank) {
            }

            @Override
            public void register() {
                //this.manager.register();
            }

            @Override
            public String getTemplateToolTipText() {
                return "Test Tool Tip";
            }

            @Override
            public String getTemplateShortName() {
                return "mockedJsonTemplate";
            }

            @Override
            public WebHookTemplateContent getTemplateForState(BuildStateEnum buildState) {
                return WebHookTemplateContent.create(buildState.getShortName(), "Template for " + buildState.getShortName(), true, "");
            }

            @Override
            public WebHookTemplateContent getBranchTemplateForState(
                    BuildStateEnum buildState) {
                return WebHookTemplateContent.create(buildState.getShortName(), "Branch template for " + buildState.getShortName(), true, "");
            }

            @Override
            public String getTemplateDescription() {
                return "A long template description";
            }

            @Override
            public Set<BuildStateEnum> getSupportedBuildStates() {
                Set<BuildStateEnum> states = new HashSet<>();
                for (BuildStateEnum state : supportedStates) {
                    states.add(state);
                }
                return states;
            }

            @Override
            public Set<BuildStateEnum> getSupportedBranchBuildStates() {
                Set<BuildStateEnum> states = new HashSet<>();
                for (BuildStateEnum state : supportedStates) {
                    states.add(state);
                }
                return states;
            }

            @Override
            public Integer getRank() {
                return 1;
            }

            @Override
            public String getPreferredDateTimeFormat() {
                return "";
            }

        };
    }

    public static WebHookMockingFramework create(BuildStateEnum buildState, ExtraParametersMap extraParameters, ExtraParametersMap teamcityProperties) {
        WebHookMockingFrameworkImpl framework = new WebHookMockingFrameworkImpl();
        framework.buildstateEnum = buildState;
        framework.extraParameters = extraParameters;
        framework.teamcityProperties = teamcityProperties;
        framework.content = new WebHookPayloadContent(framework.sBuildServer, framework.sRunningBuild, framework.previousSuccessfulBuild, buildState, extraParameters, teamcityProperties, WebHookPayloadDefaultTemplates.getDefaultEnabledPayloadTemplates());
        return framework;
    }

    @Override
    public SBuildServer getServer() {
        return sBuildServer;
    }

    @Override
    public SRunningBuild getRunningBuild() {
        return sRunningBuild;
    }

    @Override
    public WebHookPayloadContent getWebHookContent() {
        return content;
    }

    @Override
    public void loadWebHookConfigXml(File xmlConfigFile) throws JDOMException, IOException {
        //webHookConfig = new WebHookConfig(ConfigLoaderUtil.getFullConfigElement(xmlConfigFile));
        webHookConfig = ConfigLoaderUtil.getFirstWebHookInConfig(xmlConfigFile);
        this.content = new WebHookPayloadContent(this.sBuildServer, this.sRunningBuild, this.previousSuccessfulBuild, this.buildstateEnum, extraParameters, teamcityProperties, webHookConfig.getEnabledTemplates());

    }

    @Override
    public void loadWebHookProjectSettingsFromConfigXml(File xmlConfigFile) throws IOException, JDOMException {
        projSettings.readFrom(ConfigLoaderUtil.getFullConfigElement(xmlConfigFile).getChild("webhooks"));
    }

    @Override
    public WebHookConfig getWebHookConfig() {
        return webHookConfig;
    }

    @Override
    public WebHookProjectSettings getWebHookProjectSettings() {
        return projSettings;
    }

    @Override
    public WebHookPayloadManager getWebHookPayloadManager() {
        return manager;
    }

    @Override
    public SBuildType getSBuildType() {
        return sBuildType;
    }

    @Override
    public SBuildType getSBuildTypeFromSubProject() {
        return sBuildType03;
    }

    @Override
    public WebHookTemplateManager getWebHookTemplateManager() {
        return templateManager;
    }

    @Override
    public WebHookTemplateResolver getWebHookTemplateResolver() {
        return resolver;
    }

    @Override
    public WebHookListener getWebHookListener() {
        return whl;
    }

    @Override
    public WebHookAuthenticatorProvider getWebHookAuthenticatorProvider() {
        return authenticatorProvider;
    }

}
