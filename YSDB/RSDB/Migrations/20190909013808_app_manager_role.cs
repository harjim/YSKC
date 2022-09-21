using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class app_manager_role : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
               name: "companyId",
               table: "sys_session",
               nullable: false,
               defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "source",
                table: "sys_session",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                    name: "app_manager_role",
                    columns: table => new
                    {
                        id = table.Column<int>(nullable: false)
                            .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                        companyId = table.Column<int>(nullable: false),
                        msUserId = table.Column<int>(nullable: false),
                        roleId = table.Column<int>(nullable: false),
                        creatorId = table.Column<int>(nullable: false),
                        createTime = table.Column<DateTime>(nullable: false),
                        lastUpdatorId = table.Column<int>(nullable: false),
                        lastUpdateTime = table.Column<DateTime>(nullable: false),
                        remark = table.Column<string>(maxLength: 200, nullable: true)
                    },
                    constraints: table =>
                    {
                        table.PrimaryKey("PK_app_manager_role", x => x.id);
                    });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
               name: "companyId",
               table: "sys_session");

            migrationBuilder.DropColumn(
                name: "source",
                table: "sys_session");
            migrationBuilder.DropTable(
                name: "app_manager_role");
        }
    }
}
