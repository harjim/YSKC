using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createFlowAndInstanceTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "userId",
                table: "flowNode_user",
                newName: "type");

            migrationBuilder.RenameColumn(
                name: "order",
                table: "flowNode",
                newName: "type");

            migrationBuilder.AddColumn<int>(
                name: "dataId",
                table: "flowNode_user",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "mode",
                table: "flowNode",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "nextNode",
                table: "flowNode",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "prevNode",
                table: "flowNode",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "flowBranch",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    nodeId = table.Column<int>(nullable: false),
                    condition = table.Column<string>(maxLength: 50, nullable: false),
                    flowId = table.Column<int>(nullable: true),
                    seq = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowBranch", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "flowInstance",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    curNodeId = table.Column<int>(nullable: false),
                    flowId = table.Column<int>(nullable: false),
                    status = table.Column<bool>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "flowInstance_project",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    year = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    instanceId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance_project", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "flowInstance_user",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    instanceId = table.Column<int>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    nodeId = table.Column<int>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance_user", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "flowBranch");

            migrationBuilder.DropTable(
                name: "flowInstance");

            migrationBuilder.DropTable(
                name: "flowInstance_project");

            migrationBuilder.DropTable(
                name: "flowInstance_user");

            migrationBuilder.DropColumn(
                name: "dataId",
                table: "flowNode_user");

            migrationBuilder.DropColumn(
                name: "mode",
                table: "flowNode");

            migrationBuilder.DropColumn(
                name: "nextNode",
                table: "flowNode");

            migrationBuilder.DropColumn(
                name: "prevNode",
                table: "flowNode");

            migrationBuilder.RenameColumn(
                name: "type",
                table: "flowNode_user",
                newName: "userId");

            migrationBuilder.RenameColumn(
                name: "type",
                table: "flowNode",
                newName: "order");
        }
    }
}
