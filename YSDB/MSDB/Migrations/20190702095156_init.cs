using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class init : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "app_menu",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    parentId = table.Column<int>(nullable: false),
                    name = table.Column<string>(maxLength: 30, nullable: false),
                    type = table.Column<byte>(nullable: false),
                    level = table.Column<byte>(nullable: false),
                    url = table.Column<string>(maxLength: 200, nullable: true),
                    icon = table.Column<string>(maxLength: 30, nullable: false),
                    perms = table.Column<string>(nullable: true),
                    status = table.Column<byte>(nullable: false),
                    seq = table.Column<byte>(nullable: false),
                    remark = table.Column<string>(maxLength: 300, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_app_menu", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "app_role",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    roleName = table.Column<string>(maxLength: 20, nullable: false),
                    remark = table.Column<string>(maxLength: 100, nullable: true),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_app_role", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "app_role_menu",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    roleId = table.Column<int>(nullable: false),
                    menuId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_app_role_menu", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "app_user_role",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    roleId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_app_user_role", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "ys_dept",
                columns: table => new
                {
                    id = table.Column<short>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    deptName = table.Column<string>(nullable: true),
                    parentId = table.Column<short>(nullable: false),
                    level = table.Column<int>(nullable: false),
                    identity = table.Column<string>(maxLength: 80, nullable: false),
                    remark = table.Column<string>(maxLength: 300, nullable: true),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ys_dept", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "ys_user",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userName = table.Column<string>(maxLength: 20, nullable: false),
                    password = table.Column<string>(maxLength: 50, nullable: false),
                    realName = table.Column<string>(maxLength: 30, nullable: false),
                    gender = table.Column<byte>(nullable: false),
                    tel = table.Column<string>(maxLength: 30, nullable: false),
                    depId = table.Column<int>(nullable: false),
                    postion = table.Column<string>(maxLength: 20, nullable: true),
                    status = table.Column<byte>(nullable: false),
                    avatar = table.Column<string>(maxLength: 20, nullable: true),
                    remark = table.Column<string>(maxLength: 300, nullable: true),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ys_user", x => x.id);
                });
            DataContext.InitData(migrationBuilder);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "app_menu");

            migrationBuilder.DropTable(
                name: "app_role");

            migrationBuilder.DropTable(
                name: "app_role_menu");

            migrationBuilder.DropTable(
                name: "app_user_role");

            migrationBuilder.DropTable(
                name: "ys_dept");

            migrationBuilder.DropTable(
                name: "ys_user");
        }
    }
}
