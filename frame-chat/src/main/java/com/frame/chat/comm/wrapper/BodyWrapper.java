package com.frame.chat.comm.wrapper;

import com.fasterxml.jackson.databind.node.ContainerNode;

public interface BodyWrapper {
	ContainerNode<?> getBody();
	Boolean validate();
}
