<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<html>
<head>
<title>RichFaces Greeter</title>
</head>
<body>
<f:view>
	<a4j:form>
		<rich:panel header="RichFaces Greeter" style="width: 315px">
			<h:outputText value="Your name: " />
			<h:inputText
				rendered="#{dataControl_dataAccess_test['currentElement']['testTable'] != null}"
				value="#{dataControl_dataAccess_test['currentElement']['testTable']['name']}">
				<f:validateLength minimum="1" maximum="30" />
			</h:inputText>
			<a4j:commandButton value="save" reRender="greeting,dataTable"
				action="#{dataControl_dataAccess_test.save}" />
			<h:panelGroup id="greeting"
				rendered="#{dataControl_dataAccess_test['currentElement']['testTable'] != null}">
				<h:outputText value="Hello, "
					rendered="#{not empty dataControl_dataAccess_test.currentElement.testTable.name}" />
				<h:outputText
					value="#{dataControl_dataAccess_test.currentElement.testTable.name}" />
			</h:panelGroup>
			<h:panelGroup id="filters">
				<h:outputText value="Name" />
				<h:inputText
					value="#{dataControl_dataAccess_test['filterObjectMap']['testTable.name']['value']}" />
				<h:outputText value="id" />
				<h:inputText
					value="#{dataControl_dataAccess_test['filterObjectMap']['testTable.testId']['value']}" />
				<a4j:commandButton value="Find" reRender="greeting,dataTable"
					action="#{dataControl_dataAccess_test.applySearch}" />
				<a4j:commandButton value="clear"
					reRender="greeting,dataTable,filters"
					action="#{dataControl_dataAccess_test.clear}" />
			</h:panelGroup>
			<h:panelGroup id="dataTablePanel">
				<rich:dataTable id="dataTable"
					value="#{dataControl_dataAccess_test.data}" var="dataControl"
					rows="#{dataControl_dataAccess_test.pageSize}" >
					<f:facet name="caption">
						<h:outputText value="testxId" />
					</f:facet>
					<f:facet name="header">
						<h:outputText value="name" />
					</f:facet>
					<rich:column>
						<h:outputText value="#{dataControl['testTable']['testId']}" />
					</rich:column>
					<rich:column>
						<h:outputText value="#{dataControl['testTable']['name']}" />
					</rich:column>
					<f:facet name="footer">
					</f:facet>
				</rich:dataTable>
				<a4j:commandButton value="first" reRender="greeting,dataTablePanel"
					rendered="#{dataControl_dataAccess_test.prevPossible}"
					action="#{dataControl_dataAccess_test.first}" />
				<a4j:commandButton value="prev" reRender="greeting,dataTablePanel"
					rendered="#{dataControl_dataAccess_test.prevPossible}"
					action="#{dataControl_dataAccess_test.prev}" />
				<a4j:commandButton value=">" reRender="greeting,dataTablePanel"
					rendered="#{dataControl_dataAccess_test.nextPossible}"
					action="#{dataControl_dataAccess_test.next}" />
				<a4j:commandButton value=">>" reRender="greeting,dataTablePanel"
					rendered="#{dataControl_dataAccess_test.nextPossible}"
					action="#{dataControl_dataAccess_test.last}" />
			</h:panelGroup>
			<h:panelGroup id="insert">
				<a4j:commandButton value="Create"
					reRender="greeting,dataTable,insert"
					action="#{dataControl_dataAccess_test.create}" />
				<h:panelGroup id="new"
					rendered="#{not empty dataControl_dataAccess_test.newlyCreatedElement}">
					<h:outputText value="Name" />
					<h:inputText
						value="#{dataControl_dataAccess_test.newlyCreatedElement[0]['testTable']['name']}" />
					<h:outputText value="id" />
					<h:inputText
						value="#{dataControl_dataAccess_test.newlyCreatedElement[0]['testTable']['testId']}" />
					<a4j:commandButton value="save"
						reRender="greeting,dataTable,filters,insert,new"
						action="#{dataControl_dataAccess_test.save}" />
				</h:panelGroup>
			</h:panelGroup>
		</rich:panel>
	</a4j:form>
</f:view>
</body>
</html>
