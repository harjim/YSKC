using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowInstance_logTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "msg",
                table: "pushAudit",
                maxLength: 1000,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 500,
                oldNullable: true);

            migrationBuilder.AddColumn<string>(
                name: "sendUserIds",
                table: "pushAudit",
                maxLength: 600,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "moduleId",
                table: "flowInstance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "nodeStatus",
                table: "flowInstance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "flowInstance_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    instanceId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    suggestion = table.Column<string>(maxLength: 2000, nullable: true),
                    msCreatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    nodeId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance_log", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "flowInstance_log");

            migrationBuilder.DropColumn(
                name: "sendUserIds",
                table: "pushAudit");

            migrationBuilder.DropColumn(
                name: "moduleId",
                table: "flowInstance");

            migrationBuilder.DropColumn(
                name: "nodeStatus",
                table: "flowInstance");

            migrationBuilder.AlterColumn<string>(
                name: "msg",
                table: "pushAudit",
                maxLength: 500,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 1000,
                oldNullable: true);
        }
    }
}
