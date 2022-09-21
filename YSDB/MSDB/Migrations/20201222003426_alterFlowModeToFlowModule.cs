using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterFlowModeToFlowModule : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "flowMode");

            migrationBuilder.RenameColumn(
                name: "auditType",
                table: "flowNode",
                newName: "mode");

            migrationBuilder.CreateTable(
                name: "flowModule",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    moduleId = table.Column<int>(nullable: false),
                    flowId = table.Column<int>(nullable: false),
                    modeName = table.Column<string>(maxLength: 50, nullable: false),
                    type = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    seq = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowModule", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "flowModule");

            migrationBuilder.RenameColumn(
                name: "mode",
                table: "flowNode",
                newName: "auditType");

            migrationBuilder.CreateTable(
                name: "flowMode",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    createTime = table.Column<DateTime>(nullable: false),
                    flowId = table.Column<int>(nullable: false),
                    mode = table.Column<int>(nullable: false),
                    modeName = table.Column<string>(maxLength: 50, nullable: false),
                    seq = table.Column<int>(nullable: false),
                    type = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowMode", x => x.id);
                });
        }
    }
}
