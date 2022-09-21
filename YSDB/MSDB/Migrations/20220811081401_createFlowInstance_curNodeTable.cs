using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createFlowInstance_curNodeTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "flowInstance_user",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "flowInstance_user",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "flowInstance_curNode",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    instanceId = table.Column<int>(nullable: false),
                    nodeId = table.Column<int>(nullable: false),
                    nodeStatus = table.Column<int>(nullable: false),
                    flowId = table.Column<int>(nullable: false),
                    auditLevel = table.Column<int>(nullable: false),
                    auditUsers = table.Column<string>(maxLength: 200, nullable: true),
                    expired = table.Column<DateTime>(nullable: false),
                    closed = table.Column<bool>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance_curNode", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_curNode_instanceId_nodeId",
                table: "flowInstance_curNode",
                columns: new[] { "instanceId", "nodeId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "flowInstance_curNode");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "flowInstance_user");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "flowInstance_user");
        }
    }
}
