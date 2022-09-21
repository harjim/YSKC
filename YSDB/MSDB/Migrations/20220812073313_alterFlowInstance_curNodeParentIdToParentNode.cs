using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterFlowInstance_curNodeParentIdToParentNode : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "parentId",
                table: "flowInstance_curNode",
                newName: "parentNode");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "parentNode",
                table: "flowInstance_curNode",
                newName: "parentId");
        }
    }
}
