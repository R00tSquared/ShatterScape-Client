public class FriendSystem
{

		public static void addFriend(long l, Client client)
		{
				try
				{
						if (l == 0L)
								return;
						if (Client.friendsCount >= 100 && client.anInt1046 != 1)
						{
								client.pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
								return;
						}
						if (Client.friendsCount >= 200)
						{
								client.pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
								return;
						}
						String s = TextClass.fixName(TextClass.nameForLong(l));
						for (int i = 0; i < Client.friendsCount; i++)
								if (client.friendsListAsLongs[i] == l)
								{
										client.pushMessage(s + " is already on your friend list", 0, "");
										return;
								}
						for (int j = 0; j < client.ignoreCount; j++)
								if (client.ignoreListAsLongs[j] == l)
								{
										client.pushMessage("Please remove " + s + " from your ignore list first", 0, "");
										return;
								}

						if (s.equals(Client.myPlayer.getName()))
						{
								client.pushMessage("You cannot add yourself.", 0, "");
								return;
						}
						else
						{
								client.friendsList[Client.friendsCount] = s;
								client.friendsListAsLongs[Client.friendsCount] = l;
								client.friendsNodeIds[Client.friendsCount] = 0;
								Client.friendsCount++;
								client.stream.createFrame(188);
								client.stream.writeQWord(l);
								return;
						}
				}
				catch (RuntimeException runtimeexception)
				{
						SignLink.reporterror("15283, " + (byte) 68 + ", " + l + ", " + runtimeexception.toString());
				}
				throw new RuntimeException();
		}

		public static void addIgnore(long l, Client client)
		{
				try
				{
						if (l == 0L)
								return;
						if (client.ignoreCount >= 100)
						{
								client.pushMessage("Your ignore list is full. Max of 100 hit", 0, "");
								return;
						}
						String s = TextClass.fixName(TextClass.nameForLong(l));
						if (s.equals(Client.myPlayer.getName()))
						{
								client.pushMessage("You cannot add yourself.", 0, "");
								return;
						}
						for (int j = 0; j < client.ignoreCount; j++)
								if (client.ignoreListAsLongs[j] == l)
								{
										client.pushMessage(s + " is already on your ignore list", 0, "");
										return;
								}
						for (int k = 0; k < Client.friendsCount; k++)
								if (client.friendsListAsLongs[k] == l)
								{
										client.pushMessage("Please remove " + s + " from your friend list first", 0, "");
										return;
								}

						client.ignoreListAsLongs[client.ignoreCount++] = l;
						client.stream.createFrame(133);
						client.stream.writeQWord(l);
						return;
				}
				catch (RuntimeException runtimeexception)
				{
						SignLink.reporterror("45688, " + l + ", " + 4 + ", " + runtimeexception.toString());
				}
				throw new RuntimeException();
		}

		static void buildFriendChat(int j, Client client)
		{
				int l = 0;
				for (int i1 = 0; i1 < 500; i1++)
				{
						if (client.chatMessages[i1] == null)
								continue;
						if (client.chatTypeView != 2)
								continue;
						int j1 = client.chatTypes[i1];
						String s = client.chatNames[i1];
						int k1 = (70 - l * 14 + 42) + Client.chatScrollAmount + 4 + 5;
						if (k1 < -23)
								break;
						if (s != null && s.startsWith("@cr1@"))
								s = s.substring(5);
						if (s != null && s.startsWith("@cr2@"))
								s = s.substring(5);
						if (s != null && s.startsWith("@cr3@"))
								s = s.substring(5);
						if (s != null && s.startsWith("@don@"))
								s = s.substring(5);
						if ((j1 == 5 || j1 == 6) && (Client.splitPrivateChat == 0 || client.chatTypeView == 2) && (j1 == 6 || client.privateChatMode == 0 || client.privateChatMode == 1 && client.isFriendOrSelf(s)))
								l++;
						if ((j1 == 3 || j1 == 7) && (Client.splitPrivateChat == 0 || client.chatTypeView == 2) && (j1 == 7 || client.privateChatMode == 0 || client.privateChatMode == 1 && client.isFriendOrSelf(s)))
						{
								if (j > k1 - 14 && j <= k1)
								{
										client.menuActionName[client.menuActionRow] = "Add ignore @whi@" + s;
										client.menuActionID[client.menuActionRow] = 42;
										client.menuActionRow++;
										client.menuActionName[client.menuActionRow] = "Add friend @whi@" + s;
										client.menuActionID[client.menuActionRow] = 337;
										client.menuActionRow++;
								}
								l++;
						}
				}
		}

		public static boolean buildFriendsListMenu(RSInterface class9, Client client)
		{
				int i = class9.actionType;
				if (i >= 1 && i <= 200 || i >= 701 && i <= 900)
				{
						if (i >= 801)
								i -= 701;
						else if (i >= 701)
								i -= 601;
						else if (i >= 101)
								i -= 101;
						else
								i--;
						client.menuActionName[client.menuActionRow] = "Remove @whi@" + client.friendsList[i];
						client.menuActionID[client.menuActionRow] = 792;
						client.menuActionRow++;
						client.menuActionName[client.menuActionRow] = "Message @whi@" + client.friendsList[i];
						client.menuActionID[client.menuActionRow] = 639;
						client.menuActionRow++;
						return true;
				}
				if (i >= 401 && i <= 500)
				{
						client.menuActionName[client.menuActionRow] = "Remove @whi@" + class9.message;
						client.menuActionID[client.menuActionRow] = 322;
						client.menuActionRow++;
						return true;
				}
				else
				{
						return false;
				}
		}

		public static void deleteIgnore(long l, Client client)
		{
				try
				{
						if (l == 0L)
						{
								return;
						}
						for (int j = 0; j < client.ignoreCount; j++)
						{
								if (client.ignoreListAsLongs[j] == l)
								{
										client.ignoreCount--;
										System.arraycopy(client.ignoreListAsLongs, j + 1, client.ignoreListAsLongs, j, client.ignoreCount - j);
										client.stream.createFrame(74);
										client.stream.writeQWord(l);
										return;
								}
						}

						return;
				}
				catch (RuntimeException runtimeexception)
				{
						SignLink.reporterror("47229, " + 3 + ", " + l + ", " + runtimeexception.toString());
				}
				throw new RuntimeException();
		}

		public static void deleteFriend(long l, Client client)
		{
				try
				{
						if (l == 0L)
								return;
						for (int i = 0; i < Client.friendsCount; i++)
						{
								if (client.friendsListAsLongs[i] != l)
										continue;
								Client.friendsCount--;
								for (int j = i; j < Client.friendsCount; j++)
								{
										client.friendsList[j] = client.friendsList[j + 1];
										client.friendsNodeIds[j] = client.friendsNodeIds[j + 1];
										client.friendsListAsLongs[j] = client.friendsListAsLongs[j + 1];
								}

								client.stream.createFrame(215);
								client.stream.writeQWord(l);
								break;
						}
				}
				catch (RuntimeException runtimeexception)
				{
						SignLink.reporterror("18622, " + false + ", " + l + ", " + runtimeexception.toString());
						throw new RuntimeException();
				}
		}


}
