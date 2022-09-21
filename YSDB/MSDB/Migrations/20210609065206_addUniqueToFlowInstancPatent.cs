using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addUniqueToFlowInstancPatent : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_patent_patentPlanId",
                table: "flowInstance_patent",
                column: "patentPlanId",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_flowInstance_patent_patentPlanId",
                table: "flowInstance_patent");
        }
    }
}
