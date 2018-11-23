package de.arkadi.persistence.rest.hypermedia;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.Objects;

@XmlRootElement
public class LinkResource {

    private String relationship;
    private String httpType;
    private URI uri;

    public LinkResource() {
    }

    public LinkResource(@NotNull Link link) {
        this.relationship = link.getRel();
        this.httpType = link.getType();
        this.uri = link.getUri();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getHttpType() {
        return httpType;
    }

    public void setHttpType(String httpType) {
        this.httpType = httpType;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkResource that = (LinkResource) o;
        return Objects.equals(relationship, that.relationship) &&
                Objects.equals(httpType, that.httpType) &&
                Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationship, httpType, uri);
    }

    @Override
    public String toString() {
        return "LinkResource{" +
                "relationship='" + relationship + '\'' +
                ", httpType='" + httpType + '\'' +
                ", uri=" + uri +
                '}';
    }
}
