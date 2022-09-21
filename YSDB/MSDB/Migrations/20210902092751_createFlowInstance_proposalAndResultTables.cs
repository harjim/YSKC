using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createFlowInstance_proposalAndResultTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "flowInstance_proposal",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    proposalId = table.Column<int>(nullable: false),
                    instanceId = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance_proposal", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "flowInstance_result",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    documentId = table.Column<int>(nullable: false),
                    instanceId = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance_result", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_proposal_proposalId",
                table: "flowInstance_proposal",
                column: "proposalId",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_result_documentId",
                table: "flowInstance_result",
                column: "documentId",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "flowInstance_proposal");

            migrationBuilder.DropTable(
                name: "flowInstance_result");
        }
    }
}
