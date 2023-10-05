package com.altkeyx12.wols;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.text.TableRowsParser;

public class TableRowsParserTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(TableRowsParserTest.class);

	@Test
	public void test() {
		String data = 
				"NAME                UUID                                  TYPE      TIMESTAMP   TIMESTAMP-REAL               AUTOCONNECT  AUTOCONNECT-PRIORITY  READONLY  DBUS-PATH                                   ACTIVE  DEVICE  STATE      ACTIVE-PATH                                         SLAVE  FILENAME                                                               \n" + 
				"Wireless@SGx        3492bc34-4ed1-460b-9af7-3dd8ee15683c  wifi      1683766546  Thu 11 May 2023 08:55:46 AM  yes          0                     no        /org/freedesktop/NetworkManager/Settings/1  yes     wlo1    activated  /org/freedesktop/NetworkManager/ActiveConnection/2  --     /etc/NetworkManager/system-connections/Wireless@SGx.nmconnection       \n" + 
				"lo                  89091fc8-5772-4078-b119-540ff55b95a3  loopback  1683766540  Thu 11 May 2023 08:55:40 AM  no           0                     no        /org/freedesktop/NetworkManager/Settings/2  yes     lo      activated  /org/freedesktop/NetworkManager/ActiveConnection/1  --     /run/NetworkManager/system-connections/lo.nmconnection                 \n" + 
				"Wired connection 1  d561d7fd-9816-3d41-8d3a-8507381c4965  ethernet  0           never                        yes          -999                  no        /org/freedesktop/NetworkManager/Settings/3  no      --      --         --                                                  --     /run/NetworkManager/system-connections/Wired connection 1.nmconnection ";
		Map<String, String> map = new TableRowsParser().parse(data);
		
		map.forEach((k, v) -> {
			LOGGER.trace("{}, {}", k, v);
		});
		assertEquals("Wireless@SGx", map.get("0.NAME"));
		assertEquals("lo", map.get("1.NAME"));
		assertEquals("Wired connection 1", map.get("2.NAME"));
	}

}
