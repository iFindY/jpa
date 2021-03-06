package de.arkadi.persistence.rest.hypermedia;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Hypermedia {

    private List<LinkResource> links = new ArrayList<>();

    public void addLink(LinkResource linkResource) {
        this.links.add(linkResource);
    }

    public List<LinkResource> getLinks() {
        return links;
    }

    public void setLinks(List<LinkResource> links) {
        this.links = links;
    }
}
