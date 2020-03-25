<Finance>
    <Message id="${messageId}">
        <AQReq id="${id}">
            <version>${version}</version>
            <date>${date}</date>
            <#if extension ?? && extension != "">
                <extension>${extension}</extension>
            </#if>
        </AQReq>
    </Message>
</Finance>