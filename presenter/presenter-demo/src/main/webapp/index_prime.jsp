<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://primefaces.prime.com.tr/ui" prefix="p"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="cupertino/skin.css" />
</head>
<body>
<f:view>
sdfsd1
	<p:themeSwitcher initialText="Change Skin" buttonPreText="Skin: " />
	<p:accordionPanel autoHeight="false">
		<p:tab title="Godfather Part I" id="Godfather1">
			<h:panelGrid columns="2" cellpadding="10">
				<p:graphicImage value="/images/godfather/godfather1.jpg" />
				<h:outputText
					value="The story begins as Don Vito Corleone, the head of a New York Mafia family, oversees his daughter's wedding.
                    His beloved son Michael has just come home from the war, but does not intend to become part of his father's business. T
                    hrough Michael's life the nature of the family business becomes clear. The business of the family is just like the head of the family, kind and benevolent to those who give respect,
                    but given to ruthless violence whenever anything stands against the good of the family." />
			</h:panelGrid>
		</p:tab>
		<p:tab title="Godfather Part II" id="Godfather2">
			<h:panelGrid columns="2" cellpadding="10">
				<p:graphicImage value="/images/godfather/godfather2.jpg" />
				<h:outputText
					value="Francis Ford Coppola's legendary continuation and sequel to his landmark 1972 film, The_Godfather, parallels the young Vito Corleone's rise with his son Michael's spiritual fall, deepening The_Godfather's depiction of the dark side of the American dream.
                In the early 1900s, the child Vito flees his Sicilian village for America after the local Mafia kills his family. Vito struggles to make a living, legally or illegally, for his wife and growing brood in Little Italy,
                killing the local Black Hand Fanucci after he demands his customary cut of the tyro's business. With Fanucci gone, Vito's communal stature grows." />
			</h:panelGrid>
		</p:tab>
		<p:tab title="Godfather Part III" id="Godfather3">
			<h:panelGrid columns="2" cellpadding="10">
				<p:graphicImage value="/images/godfather/godfather3.jpg" />
				<h:outputText
					value="After a break of more than 15 years, director Francis Ford Coppola and writer Mario Puzo returned to the well for this third and final story of the fictional Corleone crime family.
                    Two decades have passed, and crime kingpin Michael Corleone, now divorced1 from his wife Kay has nearly succeeded in keeping his promise that his family would one day be completely legitimate." />
			</h:panelGrid>
		</p:tab>
	</p:accordionPanel>

	<p:panel id="panel" header="New Person">
		<p:messages />
		<h:panelGrid columns="3">
			<h:outputLabel for="firstname" value="Firstname: *" />
			<h:inputText id="firstname"
				value="#{dataControl_dataAccess_test.currentElement.testTable.name}"
				label="Firstname">
				<f:validateLength minimum="2" />
			</h:inputText>
		</h:panelGrid>
	</p:panel>
	
	<p:dataTable var="dataControl" emptyMessage="nothin" id="datatabletry" style="display: block" rendered="true" 
		value="#{dataControl_dataAccess_test.data}">
		<p:column>
			<h:outputText value="#{dataControl['testTable']['testId']}" />
		</p:column>
		<p:column>
			<h:outputText value="#{dataControl['testTable']['name']}" />
		</p:column>
	</p:dataTable>

</f:view>
</body>
</html>
