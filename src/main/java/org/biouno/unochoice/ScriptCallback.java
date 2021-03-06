/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 Ioannis K. Moutsatsos
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.biouno.unochoice;

import groovy.lang.GroovyShell;
import hudson.remoting.Callable;

import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * A callable (Jenkins remoting API) object that executes the script locally (when executed in the master)
 * or remotely. 
 */
public class ScriptCallback implements Callable<Object, Throwable> {

	private static final long serialVersionUID = 4524316203276099968L;
	
	private final String script;
	private Map<String, String> parameters;

	public ScriptCallback(String script, Map<String, String> parameters) {
		this.script = script;
		this.parameters = parameters;
	}
	
	public Object call() throws Throwable {
		CompilerConfiguration config = new CompilerConfiguration();
		// TODO: we can add class paths here
		GroovyShell shell = new GroovyShell(config);
		for (Entry<String, String> parameter : parameters.entrySet()) {
			shell.setVariable(parameter.getKey(), parameter.getValue());
		}
		Object eval = shell.evaluate(script);
		return eval;
	}
	
}
