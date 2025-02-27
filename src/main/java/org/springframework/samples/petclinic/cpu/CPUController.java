/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.cpu;

import java.util.List;
import java.util.Map;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CPUController {

	// @InitBinder
	// public void setAllowedFields(WebDataBinder dataBinder) {
	// dataBinder.setDisallowedFields("id");
	// }

	// @ModelAttribute("owner")
	// public Owner findOwner(@PathVariable(name = "ownerId", required = false) Integer
	// ownerId) {
	// return ownerId == null ? new Owner() : this.owners.findById(ownerId);
	// }

	// @GetMapping("/owners/new")
	// public String initCreationForm(Map<String, Object> model) {
	// Owner owner = new Owner();
	// model.put("owner", owner);
	// return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	// }

	@PostMapping("/cpu")
	public String processCpuRequest(CPURequest cpu, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/owners";
		}
		else {
			try {
				System.out.println(String.format("CPU number received is %d", cpu.getNumber()));
				int sum = fibonacci(cpu.getNumber());
				System.out.println(String.format("Result was %d", sum));
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}

		return "redirect:/owners";
	}

	private int fibonacci(int n) {
		if (n <= 1) {
			return 1;
		}

		return fibonacci(n - 1) + fibonacci(n - 2);
	}

}
