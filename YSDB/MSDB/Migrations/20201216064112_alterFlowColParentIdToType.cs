using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterFlowColParentIdToType : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "parentId",
                table: "flow",
                newName: "type");

            migrationBuilder.AddColumn<bool>(
                name: "alive",
                table: "flowInstance",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "alive",
                table: "flowInstance");

            migrationBuilder.RenameColumn(
                name: "type",
                table: "flow",
                newName: "parentId");
        }
    }
}
