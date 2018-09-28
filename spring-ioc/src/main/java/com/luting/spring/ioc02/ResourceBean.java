package com.luting.spring.ioc02;

import org.springframework.core.io.Resource;

/**
 * Author:luting
 * Date:2018-09-28 9:32
 * Description:ResourceBean
 */
public class ResourceBean {

    private Resource fileResource;

    private Resource pathResource;

    public ResourceBean() {
    }

    public Resource getFileResource() {
        return fileResource;
    }

    public void setFileResource(Resource fileResource) {
        this.fileResource = fileResource;
    }

    public Resource getPathResource() {
        return pathResource;
    }

    public void setPathResource(Resource pathResource) {
        this.pathResource = pathResource;
    }

    @Override
    public String toString() {
        return "ResourceBean{" +
                "fileResource=" + fileResource +
                ", pathResource=" + pathResource +
                '}';
    }

    public ResourceBean(Resource fileResource, Resource pathResource) {
        this.fileResource = fileResource;
        this.pathResource = pathResource;
    }
}
