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
package org.springframework.samples.petclinic.io;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;

@Controller
public class URLController {

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

	@PostMapping("/io")
	public String processIoRequest(URLRequest urlRequest, BindingResult result) {
		if (result.hasErrors()) {
			System.out
					.println(String.format("TID: %s, Received results with errors!", Thread.currentThread().getName()));
			return "redirect:/owners";
		}
		else {
			try {
				System.out.println(String.format("TID: %s, URL received is %s", Thread.currentThread().getName(),
						urlRequest.getAddress()));
				Path temp = Files.createTempFile("io-test", null);
				URL url = new URL(urlRequest.getAddress());
				HttpURLConnection huc = (HttpURLConnection) url.openConnection();
				huc.setFollowRedirects(false);
				huc.setConnectTimeout(10 * 1000);
				huc.setReadTimeout(10 * 1000);

				BufferedInputStream in = new BufferedInputStream(huc.getInputStream());
				FileOutputStream fileOutputStream = new FileOutputStream(temp.toString());
				byte dataBuffer[] = new byte[1024];
				int bytesRead;
				while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
					fileOutputStream.write(dataBuffer, 0, bytesRead);
				}
				System.out.println(String.format("TID: %s, Success!", Thread.currentThread().getName()));
			}
			catch (IOException e) {
				System.out.println(
						String.format("TID: %s, Exception! %s", Thread.currentThread().getName(), e.getMessage()));
			}

		}

		return "redirect:/owners";
	}

}
